package ca.jbrains.upfp.model.test;

import ca.jbrains.upfp.mvp.BrowseTransactionsModel;

import java.util.Collection;

public class InMemoryBrowseTransactionsModel
    implements BrowseTransactionsModel {
  private final Collection<Object> transactions;

  public InMemoryBrowseTransactionsModel(
      Collection<Object> transactions
  ) {
    this.transactions = transactions;
  }

  @Override
  public int countTransactions() {
    return 0;
  }

  @Override
  public Collection<Object> findAllTransactions() {
    return transactions;
  }
}