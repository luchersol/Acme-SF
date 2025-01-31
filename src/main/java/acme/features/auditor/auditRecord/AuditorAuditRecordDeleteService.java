
package acme.features.auditor.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.audits.AuditRecord;
import acme.entities.audits.CodeAudit;
import acme.entities.audits.Mark;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordDeleteService extends AbstractService<Auditor, AuditRecord> {

	@Autowired
	private AuditorAuditRecordRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		AuditRecord auditRecord;
		masterId = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findAuditRecordById(masterId);
		status = auditRecord != null && auditRecord.getDraftMode() && super.getRequest().getPrincipal().hasRole(auditRecord.getCodeAudit().getAuditor());
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findAuditRecordById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;
		int auditRecordId;
		CodeAudit codeAudit;
		auditRecordId = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findAuditRecordById(auditRecordId).getCodeAudit();
		super.bind(object, "code", "startDate", "endDate", "link", "mark");
		object.setCodeAudit(codeAudit);
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("codeAudit")) {
			CodeAudit ca = object.getCodeAudit();
			Auditor a = this.repository.findOneAuditorById(super.getRequest().getPrincipal().getActiveRoleId());
			boolean codeAuditIsYours = ca.getAuditor().getId() == a.getId();
			super.state(codeAuditIsYours && ca.getDraftMode(), "codeAudit", "auditor.auditRecord.form.error.codeAudit");
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		this.repository.delete(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		SelectChoices marks;

		marks = SelectChoices.from(Mark.class, object.getMark());

		Dataset dataset;

		dataset = super.unbind(object, "code", "draftMode", "startDate", "endDate", "link", "mark");
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		dataset.put("codeAudit", object.getCodeAudit());
		dataset.put("mark", marks.getSelected().getKey());
		dataset.put("marks", marks);

		super.getResponse().addData(dataset);

	}

}
