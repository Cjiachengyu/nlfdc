package com.kdl.nlfdc.action.component;

import net.sourceforge.stripes.action.Resolution;

/**
 * 新添加的身份调用时要在UserUploadRes的saveresource方法中添加userRole的一个case，设置资源的fromType
 * 
 */
public interface IUploadResource
{
    public static final String UPLOAD_RESOURCE = "/WEB-INF/jsp/component/UploadResource.jsp";

    public abstract Resolution getUploadResourcePage();
}
