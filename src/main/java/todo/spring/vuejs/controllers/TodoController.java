package todo.spring.vuejs.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.jeemv.springboot.vuejs.VueJS;
import io.github.jeemv.springboot.vuejs.utilities.Http;
import todo.spring.vuejs.repositories.ItodoRepositorie;

@Controller
@RequestMapping("/todos/")
public class TodoController {
	
	@Autowired
	private ItodoRepositorie todoRepo;
	
	@Autowired
	private VueJS vue;

	@GetMapping("")
	public String index(ModelMap map) {
		vue.addData("todos", todoRepo.findAllByOrderByPoidsDesc());
		vue.addData("dialog",false);
		vue.addData("snackbar",false);
		vue.addData("selected", new ArrayList<Integer>());
		vue.addData("message");
		vue.addData("search");
		vue.addData("text");
		vue.addDataRaw("headers", "[{text: 'id', align: 'left', sortable: false, value: 'id'},"
				+ "{text:'label', value:'label'},"
				+ "{text:'description', value:'description'},"
				+ "{text:'avancement', value:'avancement'},"
				+ "{text:'poids', value:'poids'}]");
		vue.addMethod("addTodo", "let self=this;"+Http.post("/rest/todos/", "this.text ", "self.dialog=false;"
				+ "self.message='Todos added';"
				+ "self.snackbar=true;"
				+ "self.text='';"));
		vue.addMethod("resetForm", "this.dialog=false; this.text='';");
		vue.addMethod("Mydelete", "let self=this;"
				+ "let select='';"
				+ "this.selected.forEach(function(element) {"
					+ "select+=element.id+',';"
				+ "});"
				+ "let $=' ';"+Http.delete("'/rest/todos/'+select+$", 
						"self.selected.forEach(function(element) {"
						+ "self.todos.splice(element.id,1);});"));
		map.put("vue", vue);
		return "index";
	}
}
