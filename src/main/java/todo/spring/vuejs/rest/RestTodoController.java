package todo.spring.vuejs.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import todo.spring.vuejs.entities.Ctodo;
import todo.spring.vuejs.repositories.ItodoRepositorie;

@RestController
@RequestMapping("/rest/todos/")
public class RestTodoController {
	
	@Autowired
	private ItodoRepositorie todoRepo;

	@GetMapping("")
	public List<Ctodo> list(){
		return todoRepo.findAllByOrderByPoidsDesc();
	}
	
	@GetMapping("{id}")
	public Ctodo one(@PathVariable String id) {
		Optional<Ctodo> tla =todoRepo.findById(Long.parseLong(id));
		if (tla.isPresent()) {
			return tla.get();
		}
		return null;
	}
	
	@PostMapping("")
	public @ResponseBody List<Ctodo> ajout(@RequestBody String todo) {
		todo =todo.substring(0, todo.length()-1);
		List<Ctodo> list =new ArrayList<Ctodo>();
		for (String label : todo.split("%2C")) {
			if (!label.isEmpty()) {
				todoRepo.save(new Ctodo(label));
				list.add(new Ctodo(label));
			}
		}
		return list;
	}
	
	@PutMapping("{id}")
	public @ResponseBody Ctodo modif(@PathVariable int id, @RequestBody Ctodo todo) {
		todo.setId(id);
		todoRepo.save(todo);
		return todo;
	}
	
	@PatchMapping("")
	public void labels(@RequestBody String text) {
		
	}
	
	@DeleteMapping("{ids}")
	public void delete(@PathVariable String ids) {
		for (String id : ids.split(",")) {
			todoRepo.deleteById(Long.parseLong(id));
		}
	}
}
