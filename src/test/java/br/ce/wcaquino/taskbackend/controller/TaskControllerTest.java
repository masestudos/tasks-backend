package br.ce.wcaquino.taskbackend.controller;

import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {
	
	@Mock //indica para o mockito que isso aqui é um mock
	private TaskRepo taskRepo;
	
	@InjectMocks //mocks que estiverem criados neste contexto sejem injetados nessa classe
	private TaskController controller; //global

	/*
	executado antes de cada teste, olha as anotações que possuem aqui 
	relacionadas ao moquito. Sabe que na classe TaskController deve injetar 
	os mocks então ele cria todos os mocks definidos, faz uma instância do 
	controller e vai injetar os mocks criados acima dentro do controller.
	todoRepo dentro da classe TaskController não dará mais NullPointerException 
	ao chamar o método save, ao invés de ser pelo sprint ele vai estar instanciado 
	pelo mock do mockito.
	*/
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void naoDeveSalvarTarefaSemDescricao() {
		Task todo = new Task();
		//todo.setTask("Descrição");
		todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaSemData() {
		Task todo = new Task();
		todo.setTask("Descrição");
		//todo.setDueDate(LocalDate.now());
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}
	
	@Test
	public void naoDeveSalvarTarefaComDataPassada() {
		Task todo = new Task();
		todo.setTask("Descrição");
		todo.setDueDate(LocalDate.of(2010, 01, 01));
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
	}
	
	@Test
	public void deveSalvarTarefaComSucesso() throws ValidationException {
		Task todo = new Task();
		todo.setTask("Descrição");
		todo.setDueDate(LocalDate.now());
		controller.save(todo);
		/*checagem extra, pede para o mockito verificar se o TaskRepo foi invocado no método salvar enviando o parâmetro todo,
		do contexto do controller consegue verificar um comportamento interno do serviço taskRepo */
		Mockito.verify(taskRepo).save(todo);//null
	}
}
