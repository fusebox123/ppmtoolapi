package com.cg.ppmtoolapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.ppmtoolapi.domain.Project;
import com.cg.ppmtoolapi.exception.ProjectIdException;
import com.cg.ppmtoolapi.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired 
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdate(Project project) {
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
		}
		catch(Exception e) {
			throw new ProjectIdException("Project ID: "+ project.getProjectIdentifier().toUpperCase()+" already exists");
		}
		
	}
	
	public Project findProjectByProjectIdentifier(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Project ID:" + projectIdentifier.toUpperCase()+"does not exist");
		}
		return project;
	}
	
	public Iterable<Project> getAllProjects(){
		return projectRepository.findAll();
	}
	
	public void deleteProjectByIdentifier(String projectIdentifier) {
		Project project = projectRepository.findByProjectIdentifier(projectIdentifier.toUpperCase());
		if(project == null) {
			throw new ProjectIdException("Cannot delete project with id "+ projectIdentifier.toUpperCase()+"This Project does not exist");
		}
		projectRepository.delete(project);
	}

}
