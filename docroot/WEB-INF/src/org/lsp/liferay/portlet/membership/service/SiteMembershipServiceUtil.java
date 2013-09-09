package org.lsp.liferay.portlet.membership.service;

import javax.portlet.ActionRequest;
import javax.servlet.http.HttpServletRequest;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;

public class SiteMembershipServiceUtil {
	
	private static final Log log = LogFactoryUtil.getLog(SiteMembershipServiceUtil.class);

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
	
	public static boolean shallIDisplay(long userId, long groupId) {
		boolean doDisplay = false;
		try {
			if ((userId > 0) && (groupId > 0)) {
				if (
					(GroupLocalServiceUtil.getGroup(groupId).isRegularSite())
					&& (
							!(GroupLocalServiceUtil.getGroup(groupId).getExpandoBridge().getAttribute("showJoinLeaveButton") != null)
							|| (Boolean.valueOf(GroupLocalServiceUtil.getGroup(groupId).getExpandoBridge().getAttribute("showJoinLeaveButton").toString()))
							)
					) {
					doDisplay = true;
				}
			}
		} catch (PortalException e) {
			log.error(e.getMessage(), e);
			return false;
		} catch (SystemException e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return doDisplay;
	}
	
}
