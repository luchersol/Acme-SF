
package acme.features.authenticated.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.roles.Client;
import acme.roles.TypeClients;

@Service
public class AuthenticatedClientUpdateService extends AbstractService<Authenticated, Client> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedClientRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Client.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Client client;
		Principal principal;
		int userAccountId;

		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		client = this.repository.findOneClientByUserAccountId(userAccountId);

		super.getBuffer().addData(client);
	}

	@Override
	public void bind(final Client client) {
		assert client != null;

		super.bind(client, "identification", "companyName", "type", "email", "link");
	}

	@Override
	public void validate(final Client client) {
		assert client != null;
	}

	@Override
	public void perform(final Client client) {
		assert client != null;

		this.repository.save(client);
	}

	@Override
	public void unbind(final Client client) {
		assert client != null;

		SelectChoices choicesType;
		Dataset dataset;

		choicesType = SelectChoices.from(TypeClients.class, client.getType());

		dataset = super.unbind(client, "identification", "companyName", "type", "email", "link");
		dataset.put("types", choicesType);

		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}

}