
package acme.features.sponsor.invoice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Invoice;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int invoiceId;
		Invoice invoice;
		Sponsorship sponsorship;
		Sponsor sponsor;

		invoiceId = super.getRequest().getData("id", int.class);

		sponsorship = this.repository.findOneSponsorshipByIncoiceId(invoiceId);
		invoice = this.repository.findOneInvoiceById(invoiceId);
		sponsor = invoice == null ? null : invoice.getSponsor();
		status = sponsorship != null && invoice != null && sponsorship.isDraftMode() && invoice.isDraftMode() && super.getRequest().getPrincipal().hasRole(sponsor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Invoice object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneInvoiceById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;

		super.bind(object, "code", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;

			existing = this.repository.findOneInvoiceByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "sponsor.invoice.form.error.duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date minimumDeadline;

			minimumDeadline = MomentHelper.getCurrentMoment();
			super.state(MomentHelper.isBefore(object.getDueDate(), minimumDeadline), "dueDate", "sponsor.invoice.form.error.too-close");
		}

		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(object.getQuantity().getAmount() > 0, "quantity", "sponsor.invoice.form.error.negative-salary");
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;

		object.setRegistrationTime(MomentHelper.getCurrentMoment());
		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "dueDate", "quantity", "tax", "link");

		super.getResponse().addData(dataset);
	}

}
