package ch.bbv.blog.service;

import javax.ejb.Stateless;

/**
 * Session Bean implementation class TestServiceImpl
 */
@Stateless(mappedName = "TestService")
public class TestServiceImpl implements TestService {

	/**
	 * Default constructor.
	 */
	public TestServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String sayHello() {
		return "Hi";
	}

}
