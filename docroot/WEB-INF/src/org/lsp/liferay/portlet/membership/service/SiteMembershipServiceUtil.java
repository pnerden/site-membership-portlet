package org.lsp.liferay.portlet.membership.service;

import javax.portlet.ActionRequest;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

public class SiteMembershipServiceUtil {

	public static void joinOrLeaveSite(long groupId, long userId, ActionRequest actionRequest) throws PortalException, SystemException {
		SiteMembershipService service = new SiteMembershipServiceImpl(groupId, userId, actionRequest);
		service.joinOrLeave();
	}
	
	public static int getSiteMembershipStatus(long groupId, long userId, HttpServletRequest request) throws PortalException, SystemException {
		SiteMembershipService service = new SiteMembershipServiceImpl(groupId, userId, request);
		return service.getSiteMembershipStatus();
	}
	
	public static int getSiteMembershipStatus(long groupId, long userId, ActionRequest actionRequest) throws PortalException, SystemException {
		SiteMembershipService service = new SiteMembershipServiceImpl(groupId, userId, actionRequest);
		return service.getSiteMembershipStatus();
	}
	
}
