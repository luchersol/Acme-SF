<%--
- menu.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.student1" action="https://g.co/kgs/uSjcNzE/"/>
			<acme:menu-suboption code="master.menu.student2" action="https://youtu.be/7bhKI0Mw6Yk?si=9Kc_i7S7cpkdliHp"/>
			<acme:menu-suboption code="master.menu.student3" action="https://youtu.be/NpytprpUNrw?si=1Kb0WsXVN3FZ0VPg"/>
			<acme:menu-suboption code="master.menu.student4" action="https://www.youtube.com/watch?v=MqTKBYf4qoQ"/>
			<acme:menu-suboption code="master.menu.student5" action="https://streamlabs.com/megamagolas/merch"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.risk-list" action="/authenticated/risk/list"/>
			<acme:menu-suboption code="master.menu.authenticated.notice" action="/authenticated/notice/list"/>
			<acme:menu-suboption code="master.menu.authenticated.objective" action="/authenticated/objective/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.any">
			<acme:menu-suboption code="master.menu.any.projects" action="/any/project/list-all-published"/>
			<acme:menu-suboption code="master.menu.any.claim" action="/any/claim/list-all"/>
			<acme:menu-suboption code="master.menu.any.publishedCodeAudit" action="/any/code-audit/list"/>
			<acme:menu-suboption code="master.menu.any.training-module" action="/any/training-module/list"/>
			<acme:menu-suboption code="master.menu.any.contract" action="/any/contract/list"/>
			<acme:menu-suboption code="master.menu.any.sponsorship" action="/any/sponsorship/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.banner" action="/administrator/banner/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/system/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/system/populate-sample"/>			
			<acme:menu-suboption code="master.menu.administrator.system-configuration" action="/administrator/system-configuration/show"/>		
			<acme:menu-suboption code="master.menu.administrator.objective-post" action="/administrator/objective/post"/>
			<acme:menu-suboption code="master.menu.administrator.risk-list" action="/administrator/risk/list"/>
			<acme:menu-suboption code="master.menu.administrator.dashboard" action="/administrator/administrator-form/show"/>	
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/system/shut-down"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.manager" access="hasRole('Manager')">
			<acme:menu-suboption code="master.menu.manager.project" action="/manager/project/list-mine"/>
			<acme:menu-suboption code="master.menu.manager.user-story" action="/manager/user-story/list-mine"/>
			<acme:menu-suboption code="master.menu.manager.relation" action="/manager/project-user-story/create-relation"/>
			<acme:menu-suboption code="master.menu.manager.dashboard" action="/manager/manager-form/show"/>	
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.developer" access="hasRole('Developer')">
			<acme:menu-suboption code="master.menu.developer.training-module" action="/developer/training-module/list-mine"/>
			<acme:menu-suboption code="master.menu.developer.dashboard" action="/developer/developer-form/show"/>
		</acme:menu-option>
    
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.code-audit.my-code-audits" action="/auditor/code-audit/list-mine"/>
			<acme:menu-suboption code="master.menu.auditor.dashboard" action="/auditor/auditor-form/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.client" access="hasRole('Client')">
			<acme:menu-suboption code="master.menu.client.contract" action="/client/contract/list-mine"/>
			<acme:menu-suboption code="master.menu.client.client-form" action="/client/client-form/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.sponsor" access="hasRole('Sponsor')">
			<acme:menu-suboption code="master.menu.sponsor.sponsorship" action="/sponsor/sponsorship/list-mine"/>
			<acme:menu-suboption code="master.menu.sponsor.dashboard" action="/sponsor/sponsor-form/show"/>	
		</acme:menu-option>
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/anonymous/system/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.manager" action="/authenticated/manager/update" access="hasRole('Manager')"/>
			<acme:menu-suboption code="master.menu.user-account.become-manager" action="/authenticated/manager/create" access="!hasRole('Manager')"/>
			<acme:menu-suboption code="master.menu.user-account.become-sponsor" action="/authenticated/sponsor/create" access="!hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-developer" action="/authenticated/developer/create" access="!hasRole('Developer')"/>
			<acme:menu-suboption code="master.menu.user-account.developer" action="/authenticated/developer/update" access="hasRole('Developer')"/>
			<acme:menu-suboption code="master.menu.user-account.sponsor" action="/authenticated/sponsor/update" access="hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-auditor" action="/authenticated/auditor/create" access="!hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.auditor" action="/authenticated/auditor/update" access="hasRole('Auditor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-client" action="/authenticated/client/create" access="!hasRole('Client')"/>
			<acme:menu-suboption code="master.menu.user-account.client" action="/authenticated/client/update" access="hasRole('Client')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/authenticated/system/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>
