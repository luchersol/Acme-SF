
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipListMineService extends AbstractService<Sponsor, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorSponsorshipRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Sponsorship> objects;
		int sponsorId;

		sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		objects = this.repository.findManySponsorshipsBySponsor(sponsorId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "moment", "amount");
		String published = !object.isDraftMode() ? "✓" : "x";
		dataset.put("published", published);

		super.getResponse().addData(dataset);
	}

}
