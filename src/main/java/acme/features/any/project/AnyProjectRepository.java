/*
 * AdministratorDashboardRepository.java
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface AnyProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.draftMode = FALSE")
	Collection<Project> findAllPublishedProjects();

	@Query("SELECT p FROM Project p WHERE p.draftMode = FALSE AND p.id = :id")
	Project findPublishedProjectById(int id);

}
