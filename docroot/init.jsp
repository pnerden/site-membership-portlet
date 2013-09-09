<%@page import="com.liferay.portal.service.GroupLocalServiceUtil"%>
<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.model.MembershipRequestConstants" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<%@ page import="javax.portlet.ActionRequest" %>
<%@ page import="javax.portlet.PortletURL" %>
<%@ page import="javax.portlet.WindowState" %>

<%@ page import="org.lsp.liferay.portlet.membership.portlet.SiteMembershipKeys" %>
<%@ page import="org.lsp.liferay.portlet.membership.service.SiteMembershipServiceUtil" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
String currentURL = PortalUtil.getCurrentURL(request);
long userId = PortalUtil.getUserId(request);
long groupId = themeDisplay.getScopeGroupIdOrLiveGroupId();
boolean doDisplay = SiteMembershipServiceUtil.shallIDisplay(userId, groupId);

pageContext.setAttribute("doDisplay", doDisplay);
%>