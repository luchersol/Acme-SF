
package acme.features.sponsor.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.form.SponsorForm;
import acme.roles.Sponsor;

@Controller
public class SponsorDashboardController extends AbstractController<Sponsor, SponsorForm> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private SponsorDashboardShowService showService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}
}
