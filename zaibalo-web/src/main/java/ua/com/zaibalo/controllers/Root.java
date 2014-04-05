package ua.com.zaibalo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ua.com.zaibalo.servlets.pages.IndexServlet;

@Controller
@Transactional
public class Root {
	
	@Autowired
	private IndexServlet indexServlet;
	
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public ModelAndView main(
			@RequestParam(value="categoryId", required=false) String categoryId,
			@RequestParam(value="order_by", required=false) String orderBy,
			@RequestParam(value="count", required=false) String count,
			@RequestParam(value="page", required=false) String page) {
		return indexServlet.posts(categoryId, orderBy, count, page);
	}
	
	@RequestMapping(value = { "/category/{category}" }, method = RequestMethod.GET)
	public ModelAndView caegory(
			@PathVariable("category") String categoryId) {
		return indexServlet.posts(categoryId, null, null, null);
	}
	
	@RequestMapping(value = { "/page/{page}" }, method = RequestMethod.GET)
	public ModelAndView page(
			@PathVariable("page") String page) {
		return indexServlet.posts(null, null, null, page);
	}

	@RequestMapping(value = { "/order/{order}" }, method = RequestMethod.GET)
	public ModelAndView order(
			@PathVariable("order") String order) {
		return indexServlet.posts(null, order, null, null);
	}
	
	@RequestMapping(value = { "/count/{count}" }, method = RequestMethod.GET)
	public ModelAndView count(
			@PathVariable("count") String count) {
		return indexServlet.posts(null, null, count, null);
	}

	@RequestMapping(value = { "/category/{category}/page/{page}" }, method = RequestMethod.GET)
	public ModelAndView categoryPage(
			@PathVariable("category") String categoryId,
			@PathVariable("page") String page) {
		return indexServlet.posts(categoryId, null, null, page);
	}
	
	@RequestMapping(value = { "/count/{count}/page/{page}" }, method = RequestMethod.GET)
	public ModelAndView countPage(
			@PathVariable("count") String count,
			@PathVariable("page") String page) {
		return indexServlet.posts(null, null, count, page);
	}
	
	@RequestMapping(value = { "/order/{order}/page/{page}" }, method = RequestMethod.GET)
	public ModelAndView orderPage(
			@PathVariable("order") String order,
			@PathVariable("page") String page) {
		return indexServlet.posts(null, order, null, page);
	}
	
	@RequestMapping(value = { "/order/{order}/count/{count}" }, method = RequestMethod.GET)
	public ModelAndView orderCount(
			@PathVariable("order") String order,
			@PathVariable("count") String count) {
		return indexServlet.posts(null, order, count, null);
	}
	
	@RequestMapping(value = { "/category/{category}/count/{count}" }, method = RequestMethod.GET)
	public ModelAndView categoryCount(
			@PathVariable("category") String categoryId,
			@PathVariable("count") String count) {
		return indexServlet.posts(categoryId, null, count, null);
	}
	
	@RequestMapping(value = { "/order/{order}/count/{count}/page/{page}" }, method = RequestMethod.GET)
	public ModelAndView orderCountPage(
			@PathVariable("order") String order,
			@PathVariable("count") String count,
			@PathVariable("page") String page) {
		return indexServlet.posts(null, order, count, page);
	}

	@RequestMapping(value = { "/category/{category}/order/{order}" }, method = RequestMethod.GET)
	public ModelAndView categoryOrder(
			@PathVariable("category") String categoryId,
			@PathVariable("order") String order) {
		return indexServlet.posts(categoryId, order, null, null);
	}

	@RequestMapping(value = { "/category/{category}/order/{order}/page/{page}" }, method = RequestMethod.GET)
	public ModelAndView categoryOrderPage(
			@PathVariable("category") String categoryId,
			@PathVariable("order") String order,
			@PathVariable("page") String page) {
		return indexServlet.posts(categoryId, order, null, page);
	}
	
	@RequestMapping(value = { "/category/{category}/count/{count}/page/{page}" }, method = RequestMethod.GET)
	public ModelAndView categoryCountPage(
			@PathVariable("category") String categoryId,
			@PathVariable("count") String count,
			@PathVariable("page") String page) {
		return indexServlet.posts(categoryId, null, count, page);
	}
	
	@RequestMapping(value = { "/category/{category}/order/{order}/count/{count}" }, method = RequestMethod.GET)
	public ModelAndView categoryOrderCount(
			@PathVariable("category") String categoryId,
			@PathVariable("order") String order,
			@PathVariable("count") String count) {
		return indexServlet.posts(categoryId, order, count, null);
	}
	
	@RequestMapping(value = { "/category/{category}/order/{order}/count/{count}/page/{page}" }, method = RequestMethod.GET)
	public ModelAndView categoryOrderCountPage(
			@PathVariable("category") String categoryId,
			@PathVariable("order") String order,
			@PathVariable("count") String count,
			@PathVariable("page") String page) {
		return indexServlet.posts(categoryId, order, count, page);
	}
}
