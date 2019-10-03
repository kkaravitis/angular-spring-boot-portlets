/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package book.service.persistence.test;

import book.exception.NoSuchBookException;

import book.model.Book;

import book.service.persistence.BookPersistence;
import book.service.persistence.BookUtil;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @generated
 */
@RunWith(Arquillian.class)
public class BookPersistenceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED, "book.service"));

	@Before
	public void setUp() {
		_persistence = BookUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Book> iterator = _books.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		String pk = RandomTestUtil.randomString();

		Book book = _persistence.create(pk);

		Assert.assertNotNull(book);

		Assert.assertEquals(book.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Book newBook = addBook();

		_persistence.remove(newBook);

		Book existingBook = _persistence.fetchByPrimaryKey(
			newBook.getPrimaryKey());

		Assert.assertNull(existingBook);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addBook();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		String pk = RandomTestUtil.randomString();

		Book newBook = _persistence.create(pk);

		newBook.setUuid(RandomTestUtil.randomString());

		newBook.setTitle(RandomTestUtil.randomString());

		newBook.setAuthor(RandomTestUtil.randomString());

		_books.add(_persistence.update(newBook));

		Book existingBook = _persistence.findByPrimaryKey(
			newBook.getPrimaryKey());

		Assert.assertEquals(existingBook.getUuid(), newBook.getUuid());
		Assert.assertEquals(existingBook.getIsbn(), newBook.getIsbn());
		Assert.assertEquals(existingBook.getTitle(), newBook.getTitle());
		Assert.assertEquals(existingBook.getAuthor(), newBook.getAuthor());
	}

	@Test
	public void testCountByUuid() throws Exception {
		_persistence.countByUuid("");

		_persistence.countByUuid("null");

		_persistence.countByUuid((String)null);
	}

	@Test
	public void testCountByAuthor() throws Exception {
		_persistence.countByAuthor("");

		_persistence.countByAuthor("null");

		_persistence.countByAuthor((String)null);
	}

	@Test
	public void testCountByTitle() throws Exception {
		_persistence.countByTitle("");

		_persistence.countByTitle("null");

		_persistence.countByTitle((String)null);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Book newBook = addBook();

		Book existingBook = _persistence.findByPrimaryKey(
			newBook.getPrimaryKey());

		Assert.assertEquals(existingBook, newBook);
	}

	@Test(expected = NoSuchBookException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		String pk = RandomTestUtil.randomString();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<Book> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create(
			"BOOK_Book", "uuid", true, "isbn", true, "title", true, "author",
			true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Book newBook = addBook();

		Book existingBook = _persistence.fetchByPrimaryKey(
			newBook.getPrimaryKey());

		Assert.assertEquals(existingBook, newBook);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		String pk = RandomTestUtil.randomString();

		Book missingBook = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingBook);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {

		Book newBook1 = addBook();
		Book newBook2 = addBook();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBook1.getPrimaryKey());
		primaryKeys.add(newBook2.getPrimaryKey());

		Map<Serializable, Book> books = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(2, books.size());
		Assert.assertEquals(newBook1, books.get(newBook1.getPrimaryKey()));
		Assert.assertEquals(newBook2, books.get(newBook2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {

		String pk1 = RandomTestUtil.randomString();

		String pk2 = RandomTestUtil.randomString();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Book> books = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(books.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {

		Book newBook = addBook();

		String pk = RandomTestUtil.randomString();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBook.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Book> books = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, books.size());
		Assert.assertEquals(newBook, books.get(newBook.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys() throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Book> books = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertTrue(books.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey() throws Exception {
		Book newBook = addBook();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newBook.getPrimaryKey());

		Map<Serializable, Book> books = _persistence.fetchByPrimaryKeys(
			primaryKeys);

		Assert.assertEquals(1, books.size());
		Assert.assertEquals(newBook, books.get(newBook.getPrimaryKey()));
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting() throws Exception {
		Book newBook = addBook();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Book.class, _dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("isbn", newBook.getIsbn()));

		List<Book> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Book existingBook = result.get(0);

		Assert.assertEquals(existingBook, newBook);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Book.class, _dynamicQueryClassLoader);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("isbn", RandomTestUtil.randomString()));

		List<Book> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting() throws Exception {
		Book newBook = addBook();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Book.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("isbn"));

		Object newIsbn = newBook.getIsbn();

		dynamicQuery.add(
			RestrictionsFactoryUtil.in("isbn", new Object[] {newIsbn}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingIsbn = result.get(0);

		Assert.assertEquals(existingIsbn, newIsbn);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Book.class, _dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("isbn"));

		dynamicQuery.add(
			RestrictionsFactoryUtil.in(
				"isbn", new Object[] {RandomTestUtil.randomString()}));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Book addBook() throws Exception {
		String pk = RandomTestUtil.randomString();

		Book book = _persistence.create(pk);

		book.setUuid(RandomTestUtil.randomString());

		book.setTitle(RandomTestUtil.randomString());

		book.setAuthor(RandomTestUtil.randomString());

		_books.add(_persistence.update(book));

		return book;
	}

	private List<Book> _books = new ArrayList<Book>();
	private BookPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;

}