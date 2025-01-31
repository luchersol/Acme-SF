
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int contractId;
		Contract contract;
		Client client;

		contractId = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractById(contractId);
		client = contract == null ? null : contract.getClient();
		status = contract != null && contract.getDraftMode() && super.getRequest().getPrincipal().hasRole(client);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Contract contract;
		int id;

		id = super.getRequest().getData("id", int.class);
		contract = this.repository.findOneContractById(id);

		super.getBuffer().addData(contract);
	}

	@Override
	public void bind(final Contract contract) {
		assert contract != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(contract, "code", "instantiationMoment", "providerName", "customerName", "goal", "budget");
		contract.setProject(project);
	}

	@Override
	public void validate(final Contract contract) {
		assert contract != null;

		boolean state;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Contract existing;

			existing = this.repository.findOneContractByCode(contract.getCode());
			super.state(existing == null || existing.getId() == contract.getId(), "code", "client.contract.form.error.code");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			state = contract.getBudget().getAmount() >= 0;
			super.state(state, "budget", "client.contract.form.error.budget");
		}

		super.state(!this.repository.findManyProgressLogByMasterId(contract.getId()).isEmpty(), "code", "client.contract.form.error.publish");
		boolean allContractInDraftMode = this.repository.areAllProgressLogPublished(contract.getId());
		super.state(allContractInDraftMode, "code", "client.contract.form.error.allpublish");

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			Collection<Money> budgets = this.repository.areAllBudgetContractExcedCostProject(contract.getProject().getId());
			state = budgets.isEmpty() && contract.getBudget().getAmount() < contract.getProject().getCost().getAmount()
				|| budgets.stream().map(x -> x.getAmount()).mapToDouble(x -> x.doubleValue()).sum() + contract.getBudget().getAmount() < contract.getProject().getCost().getAmount();
			super.state(state, "budget", "client.contract.form.error.budgetExcedCostProject");
		}

	}

	@Override
	public void perform(final Contract contract) {
		assert contract != null;

		contract.setDraftMode(false);
		this.repository.save(contract);
	}

	@Override
	public void unbind(final Contract contract) {
		assert contract != null;

		Collection<Project> projectAllPublish;
		SelectChoices choicesProject;
		Dataset dataset;

		projectAllPublish = this.repository.findAllProjectsPublish();

		choicesProject = SelectChoices.from(projectAllPublish, "title", contract.getProject());

		dataset = super.unbind(contract, "code", "instantiationMoment", "providerName", "customerName", "goal", "budget");
		dataset.put("project", choicesProject.getSelected().getKey());
		dataset.put("projects", choicesProject);

		super.getResponse().addData(dataset);
	}

}
