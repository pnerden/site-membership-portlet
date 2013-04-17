package org.lsp.liferay.portlet.membership.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.lsp.liferay.portlet.membership.service.SiteMembershipServiceUtil;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class SiteMembershipPortlet extends MVCPortlet {

	public void joinOrLeave(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		long userId = themeDisplay.getUserId();
		long groupId = themeDisplay.getScopeGroupIdOrLiveGroupId();
		
		SiteMembershipServiceUtil.joinOrLeaveSite(groupId, userId, actionRequest);
		
	}
}
