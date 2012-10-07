package com.ononedb.nextweb.plugins.impl;

import io.nextweb.Link;
import io.nextweb.LinkList;
import io.nextweb.LinkListQuery;
import io.nextweb.ListQuery;
import io.nextweb.Node;
import io.nextweb.Query;
import io.nextweb.fn.AsyncResult;
import io.nextweb.fn.BooleanResult;
import io.nextweb.fn.Calculation;
import io.nextweb.fn.Maybe;

import java.util.ArrayList;
import java.util.List;

import com.ononedb.nextweb.OnedbEntityList;
import com.ononedb.nextweb.common.H;
import com.ononedb.nextweb.plugins.Plugin_EntityList_Select;

public class P_EntityList_Select implements
		Plugin_EntityList_Select<OnedbEntityList> {

	private OnedbEntityList entity;

	public P_EntityList_Select() {
		super();

	}

	@Override
	public void injectObject(final OnedbEntityList obj) {
		this.entity = obj;
	}

	@Override
	public ListQuery select(final Link propertyType) {

		final Calculation<Node, Query> queryOperation = new Calculation<Node, Query>() {

			@Override
			public Query apply(final Node input) {
				return input.select(propertyType);
			}
		};

		return ListCommon.joinNodeResults(entity, queryOperation);
	}

	@Override
	public ListQuery selectAll(final Link propertyType) {
		final Calculation<Node, ListQuery> queryOperation = new Calculation<Node, ListQuery>() {

			@Override
			public ListQuery apply(final Node input) {
				return input.selectAll(propertyType);
			}
		};

		return ListCommon.joinListResults(entity, queryOperation);
	}

	@Override
	public LinkListQuery selectAllLinks() {

		return ListCommon.joinGeneric(entity,
				new Calculation<Node, LinkListQuery>() {

					@Override
					public LinkListQuery apply(final Node input) {
						return input.selectAllLinks();
					}
				}, new Calculation<List<Maybe<LinkList>>, LinkList>() {

					@Override
					public LinkList apply(final List<Maybe<LinkList>> input) {
						final List<LinkList> lists = Maybe.allValues(input);
						final List<Link> links = new ArrayList<Link>(0);

						for (final LinkList list : lists) {
							links.addAll(list.asList());
						}

						return H.factory(entity).createLinkList(
								H.session(entity),
								entity.getExceptionManager(), links);
					}
				}, new Calculation<AsyncResult<LinkList>, LinkListQuery>() {

					@Override
					public LinkListQuery apply(final AsyncResult<LinkList> input) {
						return H.factory(entity).createLinkListQuery(
								H.session(entity),
								entity.getExceptionManager(), input);
					}
				});
	}

	@Override
	public ListQuery selectAll() {
		final Calculation<Node, ListQuery> queryOperation = new Calculation<Node, ListQuery>() {

			@Override
			public ListQuery apply(final Node input) {
				return input.selectAll();
			}
		};

		return ListCommon.joinListResults(entity, queryOperation);
	}

	@Override
	public BooleanResult has(final Link propertyType) {

		final Calculation<Node, BooleanResult> operation = new Calculation<Node, BooleanResult>() {

			@Override
			public BooleanResult apply(final Node input) {

				return input.has(propertyType);
			}
		};
		final Calculation<List<Maybe<Boolean>>, Boolean> joinOperation = new Calculation<List<Maybe<Boolean>>, Boolean>() {

			@Override
			public Boolean apply(final List<Maybe<Boolean>> input) {

				final List<Boolean> list = Maybe.allValues(input);

				for (final Boolean result : list) {
					if (result.booleanValue() == true) {
						return true;
					}
				}

				return false;
			}
		};
		final Calculation<AsyncResult<Boolean>, BooleanResult> createResultOperation = new Calculation<AsyncResult<Boolean>, BooleanResult>() {

			@Override
			public BooleanResult apply(final AsyncResult<Boolean> input) {

				return H.factory(entity).createBooleanResult(
						entity.getExceptionManager(), entity.getSession(),
						input);

			}
		};
		return ListCommon.joinGeneric(entity, operation, joinOperation,
				createResultOperation);
	}

}
