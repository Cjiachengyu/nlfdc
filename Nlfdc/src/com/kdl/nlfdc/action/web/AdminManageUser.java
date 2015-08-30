package com.kdl.nlfdc.action.web;

import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SessionScope;
import net.sourceforge.stripes.action.UrlBinding;

import org.springframework.dao.DuplicateKeyException;

import com.kdl.nlfdc.action.AbstractActionBean;
import com.kdl.nlfdc.action.Constants;
import com.kdl.nlfdc.action.Utils;
import com.kdl.nlfdc.domain.Admin;
import com.kdl.nlfdc.domain.AdminMenu;
import com.kdl.nlfdc.domain.FirstMenu;
import com.kdl.nlfdc.domain.User;
import com.kdl.nlfdc.exception.SqlAffectedCountException;

/**
 * 超级管理员和普通管理员都用这个类
 * 
 * @author Administrator
 * 
 * @date：2015年8月21日
 */
@SessionScope
@UrlBinding("/adminusermanageaction")
public class AdminManageUser extends AbstractActionBean
{
    private static final long serialVersionUID = 8414646913625339158L;

    private static final String MANAGE_USER = "/WEB-INF/jsp/admin/AdminUserManage.jsp";
    private static final String USER_LISTVIEW = "/WEB-INF/jsp/admin/AdminListView.jsp";
    protected static final String ADD_ADMIN_PAGE = "/WEB-INF/jsp/admin/AdminProblemManage.jsp";
    protected static final String ADMIN_MENU_PAGE = "/WEB-INF/jsp/admin/AdminMenuListView.jsp";

    
    private List<Admin> adminList;
    private List<FirstMenu> allFirstMenuList;
    
    public List<Admin> getAdminList()
    {
        return adminList;
    }
    
    public List<FirstMenu> getAllFirstMenuList()
    {
        return allFirstMenuList;
    }

    // resolution
    // --------------------------------------------------------------------------------
    @DefaultHandler
    @HandlesEvent("manageuser")
    public Resolution manageUser()
    {
        logRequest();
        
        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        getCurrentSession().setAttribute("currentMemuOperation", Constants.MainMenuOperation.ADMIN_MANAGE);
        
        allFirstMenuList = cmService.getAllFirstMenu(); 
        
        refreshAdminList();
        return new ForwardResolution(MANAGE_USER);
    }
   
    @HandlesEvent("turntoaddadminpage")
    public Resolution turnToAddAdminPage()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }

        return new ForwardResolution(ADD_ADMIN_PAGE);
    }

    @HandlesEvent("disableadmin")
    public Resolution disableAdmin()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }
        
        int adminId = getParamInt("adminId");
        
        cmService.disableAdmin(adminId);
        
        refreshAdminList();
        return new ForwardResolution(USER_LISTVIEW);
    }
    
    @HandlesEvent("enableadmin")
    public Resolution enableAdmin()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }
        
        int adminId = getParamInt("adminId");
        
        cmService.enableAdmin(adminId);
        
        refreshAdminList();
        return new ForwardResolution(USER_LISTVIEW);
    }
    
    @HandlesEvent("getsetadminmenuview")
    public Resolution getSetAdminMenuView()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getYjLogoutResolution();
        }
        
        int adminId = getParamInt("adminId");
        
        List<AdminMenu> adminMenuList = cmService.getAdminMenus(adminId);
        
        for(AdminMenu adminMenu: adminMenuList)
        {
            for(FirstMenu firstMenu: allFirstMenuList)
            {
                if (adminMenu.getFirstMenuId() == firstMenu.getFirstMenuId())
                {
                    firstMenu.setIsSelected(true);
                    break;
                }
            }
        }
        
        return new ForwardResolution(ADMIN_MENU_PAGE);
    }
    
    @HandlesEvent("saveadminmenus")
    public Resolution saveAdminMenus()
    {
        logRequest();
        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        int adminId = getParamInt("adminId", -1);
        String firstMenuIds = getParam("firstMenuIds", "null");
        if (adminId == -1 || firstMenuIds.equals("null"))
        {
            return getStringResolution("dataError"); // 参数不合法
        }

        try
        {
            doSaveAdminMenus(adminId, firstMenuIds);

            refreshAdminList();
            return getStringResolution("ok");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }
    }
    
    @HandlesEvent("getuserlistview")
    public Resolution getUserListView()
    {
        return new ForwardResolution(USER_LISTVIEW);
    }
    
    
    
    
    
    
    /**
     * 添加系统题库编辑
     * 
     * @return
     */
    @HandlesEvent("addnewsystemuser")
    public Resolution addNewSystemUser()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String loginName = getParam("loginName", "");
        String password = getParam("password", "");
        String userName = getParam("userName", "");
        int userRole = getParamInt("userRole", 0);

        if (loginName.equals("") || password.equals("") || userName.equals(""))
        {
            return getStringResolution("error");
        }
        if (loginName.length() > Constants.MaxLength.LOGIN_NAME ||
            password.length() > Constants.MaxLength.PASSWORD ||
            userName.length() > Constants.MaxLength.USER_NAME)
        {
            return getStringResolution("lengthException");
        }

        User systemUser = new User();
        systemUser.setLoginName(loginName);
        systemUser.setPassword(password);
        systemUser.setUserName(userName);
        systemUser.setUserRole(userRole);

        try
        {
            cmService.insertUserAndGetUserId(systemUser);
            // 插入成功
            int userId = systemUser.getUserId();
            if (userRole == Constants.UserRole.YJ_EDITOR)
            {
                systemUser.setLoginId(userId + "e");
            }

            cmService.updateUser(systemUser);
            return getStringResolution("ok");
        }
        catch (DuplicateKeyException e)
        {
            return getStringResolution("dupkey");
        }
        catch (Exception e)
        {
            return getStringResolution("error");
        }

    }

    /**
     * 添加编辑时ajax检查loginName是否可用
     * 返回"ok" -> 可以使用
     * 返回"exist" -> 不可以使用
     * 
     * @return
     */
    @HandlesEvent("checkloginnameunique")
    public Resolution checkLoginNameUnique()
    {
        logRequest();

        if (!sessionIsValid())
        {
            return getStringTimeoutResolution();
        }

        String loginName = getParam("loginName");
        
        Admin admin = cmService.getAdminByLoginNameIgnoreDelete(loginName);
        if (admin == null)
        {
            return getStringResolution("ok");
        }
        else
        {
            return getStringResolution("exist");
        }
    }
   
    
    // private 
    // ------------------------------------------------------------------------
    private void refreshAdminList()
    {
        log("super admin select all common admin "); 

        adminList = cmService.getAllCommonAdmin();
        
        for (Admin admin : adminList)
        {
            admin.setAdminMenuList(cmService.getAdminMenus(admin.getAdminId()));
        }

        log("common admin list size: " + adminList.size());
    }
    
    private void doSaveAdminMenus(int adminId, String firstMenuIds) throws SqlAffectedCountException
    {
        // 先把该老师的所有userSubject都删除，再把这次选中的subject重新插入
        cmService.deleteAdminMenu(adminId);

        if (!firstMenuIds.equals("nochecked"))
        {
            firstMenuIds = Utils.trimEnd(firstMenuIds, 1);
            String[] firstMenuIdStr = firstMenuIds.split(",");
            for (String firstMenuId : firstMenuIdStr)
            {
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.setAdminId(adminId);
                adminMenu.setFirstMenuId(Integer.parseInt(firstMenuId));
                cmService.insertAdminMenu(adminMenu);
            }
        }
    }
    
    // override
    // ------------------------------------------------------------------------
    @Override
    protected boolean sessionIsValid()
    {
        return makeSureSuperAdmin() || makeSureCommonAdmin();
    }
    
}
