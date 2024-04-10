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

package acme.features.manager.userStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.UserStory;

@Repository
public interface ManagerUserStoryRepository extends AbstractRepository {

	@Query("SELECT pu.userStory FROM ProjectUserStory pu WHERE pu.project.id = :projectId")
	Collection<UserStory> findUserStoriesByProjectId(int projectId);

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findOneUserStoryById(int id);
}
