<%--
- form.jsp
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
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="${true}">
	<acme:input-textbox code="developer.training.form.label.code" path="code"/>	
	<acme:input-textbox code="developer.training.form.label.title" path="title"/>
	<acme:input-textarea code="developer.training.form.label.abstractProject" path="abstractProject"/>
	<acme:input-checkbox code="developer.training.form.label.indication" path="indication"/>
	<acme:input-money code="developer.training.form.label.cost" path="cost"/>
	<acme:input-url code="developer.training.form.label.link" path="link"/>
</acme:form>
