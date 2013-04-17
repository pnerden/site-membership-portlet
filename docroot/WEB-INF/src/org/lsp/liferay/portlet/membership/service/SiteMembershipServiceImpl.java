package org.lsp.liferay.portlet.membership.service;

import javax.portlet.ActionRequest;
import javax.servlet.http.HttpServletRequest;

import org.lsp.liferay.portlet.membership.portlet.SiteMembershipKeys;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.util.PortalUtil;

public class SiteMembershipServiceImpl implements SiteMembershipService {

	private long groupId;
	private long userId;
	private long companyId;
	private ServiceContext serviceContext;
	private long[] userIds = new long[1];
	private Group group;
	private User user;
	
	private static final Log log = LogFactoryUtil
			.getLog(SiteMembershipServiceImpl.class);
	
	private static final String OPEN_STATUS_COMMENT = "Automatically opened demand";
	
	public SiteMembershipServiceImpl(long groupId, long userId, HttpServletRequest request) throws PortalException, SystemException {
		this.serviceContext = ServiceContextFactory.getInstance(request);
		this.companyId = serviceContext.getCompanyId();
		this.init(groupId, userId);
	}
	
	public SiteMembershipServiceImpl(long groupId, long userId, ActionRequest actionRequest) throws PortalException, SystemException {
		this.companyId = PortalUtil.getCompanyId(actionRequest);
		this.serviceContext = ServiceContextFactory.getInstance(actionRequest);
		this.init(groupId, userId);
	}
	
	private void init(long groupId, long userId) throws PortalException, SystemException {
		this.groupId = groupId;
		this.userId = userId;		
		this.userIds[0] = userId;
		this.group = GroupLocalServiceUtil.getGroup(groupId);
		this.user = UserLocalServiceUtil.getUser(userId);
	}
	
	@Override
	public void joinOrLeave() {
		try {
			if (user.getGroups().contains(group)) {
				
				this.leaveSite();
				
			} else {
			
				if (group.getType() == SiteMembershipKeys.MEMBERSHIP_TYPE_PUBLIC) {
					this.joinSite();
				} else {
					this.askMembership();
				}
				
			}
			
		} catch (PortalException e) {
			log.error(e.getMessage(), e);
		} catch (SystemException e) {
			log.error(e.getMessage(), e);
		}
	}	
	
	private void joinSite() throws PortalException, SystemException {	
		log.debug("User "+userId+" has requested joining site "+groupId);
		UserServiceUtil.addGroupUsers(groupId, userIds, serviceContext);
		
	}
	
	private void leaveSite() throws SystemException, PortalException {
		log.debug("User "+userId+" has requested joining site "+groupId);
		UserServiceUtil.unsetGroupUsers(groupId, userIds, serviceContext);
	}
	
	private void askMembership() throws SystemException, PortalException {
		log.debug("User "+userId+" has requested membership to site "+groupId);
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(MembershipRequest.class)
				.add(PropertyFactoryUtil.forName("groupId").eq(groupId))
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("companyId").eq(companyId))
				.add(PropertyFactoryUtil.forName("statusId").eq(MembershipRequestConstants.STATUS_PENDING))
				;
		if (MembershipRequestLocalServiceUtil.dynamicQueryCount(query) == 0) {
			MembershipRequestServiceUtil.addMembershipRequest(groupId, OPEN_STATUS_COMMENT, serviceContext);
		}
	}

	@Override
	public int getSiteMembershipStatus() {
		try {
			if (GroupLocalServiceUtil.hasUserGroup(userId, groupId)) {
				log.debug("User is already a member of group: "+groupId);
				return SiteMembershipKeys.STATUS_APPROVED;
			}
			if (group.getType() == SiteMembershipKeys.MEMBERSHIP_TYPE_PRIVATE) {
				log.debug("Group "+groupId+" is private. No joining available");
				return SiteMembershipKeys.REQUEST_IMPOSSIBLE;
			}
			if (MembershipRequestLocalServiceUtil.hasMembershipRequest(userId, groupId, SiteMembershipKeys.STATUS_PENDING)) {
				log.debug("User membership for group "+groupId+" request is waiting for approval");
				return SiteMembershipKeys.STATUS_PENDING;
			}
		} catch (SystemException e) {
			log.error(e.getMessage(), e);
		}
		log.debug("User is not a member of group: "+groupId);
		return SiteMembershipKeys.NOT_A_MEMBER;
	}

}
