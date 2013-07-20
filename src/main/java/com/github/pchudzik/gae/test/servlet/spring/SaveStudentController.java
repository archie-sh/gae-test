package com.github.pchudzik.gae.test.servlet.spring;

import com.github.pchudzik.gae.test.dao.StudentDao;
import com.github.pchudzik.gae.test.domain.Student;
import com.github.pchudzik.gae.test.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * User: pawel
 * Date: 20.07.13
 * Time: 11:27
 */
@Controller
@RequestMapping("/raw/student")
public class SaveStudentController {
	@Autowired private StudentDao studentDao;
	@Autowired private StudentRepository studentRepository;

	@RequestMapping(value = "/edit", method = GET)
	public String getEditPage() {
		return "/student/edit";
	}

	@RequestMapping(value = "/edit", method = POST)
	public String saveStudent(Student student, ModelMap modelMap,
							  @RequestParam String saveMethod) {

		String msg = null;
		//ugly
		if("Save raw".equals(saveMethod)) {
			studentDao.save(student);
			msg = "Student saved in store using raw entity manager";
		} else if("Save repo".equals(saveMethod)) {
			studentRepository.save(student);
			msg = "Student saved in store using spring data";
		}

		modelMap.put("msg", msg);
		modelMap.put("object", student);
		
		return "/success";
	}

	@ModelAttribute("student")
	public Student getStudent() {
		return new Student();
	}
}
