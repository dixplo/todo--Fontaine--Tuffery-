package todo.spring.vuejs.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import todo.spring.vuejs.entities.Ctodo;

public interface ItodoRepositorie extends JpaRepository<Ctodo, Long> {
	public List<Ctodo> findAllByOrderByPoidsDesc();
}
