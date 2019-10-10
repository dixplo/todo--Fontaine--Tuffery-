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
		vue.addData("dialogform",false);
		vue.addData("snackbar",false);
		vue.addData("myform", false);
		vue.addData("selected", new ArrayList<Integer>());
		vue.addData("message");
		vue.addData("search");
		vue.addData("items");
		vue.addData("text");
		vue.addDataRaw("editedItem", "{id: 0, label: '', description: '',avancement: 0, poids: 0}");
		vue.addDataRaw("headers", "[{text: 'id', align: 'left', sortable: false, value: 'id'},"
				+ "{text:'label', value:'label'},"
				+ "{text:'description', value:'description'},"
				+ "{text:'avancement', value:'avancement'},"
				+ "{text:'poids', value:'poids'},"
				+ "{ text: 'Actions', value: 'action', sortable: false }]");
		vue.addMethod("addTodo", "let self=this;"+Http.post("/rest/todos/", "this.text ", "self.dialog=false;"
				+ "self.message='Todos added';"
				+ "self.snackbar=true;"
				+ "self.text='';"
				+ "console.log(response.data);"
				+ "response.data.forEach(function(bla) {"
					+ "self.todos.push(bla);"
				+ "});"));
		vue.addMethod("resetForm", "this.dialog=false; this.text='';");
		vue.addMethod("Mydelete", "let self=this;"
				+ "let $=' ';"+Http.delete("'/rest/todos/'+item.id+$", 
						"self.todos.splice(item.id,1);"), "item");
		vue.addMethod("Editing", "let $=' ';let self=this;"+Http.put("'/rest/todos/'+self.items.id+$",(Object) "self.editedItem" ,"self.dialogform=false;"));
		vue.addMethod("myitem", "this.items =item;this.editedItem =item; this.dialogform =true;", "item");
		map.put("vue", vue);
		return "index";
	}
}
