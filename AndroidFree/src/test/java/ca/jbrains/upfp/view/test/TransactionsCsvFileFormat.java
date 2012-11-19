package ca.jbrains.upfp.view.test;

import ca.jbrains.upfp.Conveniences;
import ca.jbrains.upfp.model.test.Transaction;
import com.google.common.base.*;
import com.google.common.collect.*;
import com.sun.istack.internal.Nullable;

import java.util.List;

public class TransactionsCsvFileFormat {

  private CsvHeaderFormat csvHeaderFormat;
  private CsvFormat<Transaction> transactionCsvFormat;

  public TransactionsCsvFileFormat(
      CsvHeaderFormat csvHeaderFormat,
      final CsvFormat<Transaction> transactionCsvFormat
  ) {
    this.csvHeaderFormat = csvHeaderFormat;
    this.transactionCsvFormat = transactionCsvFormat;
  }

  public String formatTransactionsAsCsvFile(
      List<Transaction> transactions
  ) {
    final List<String> lines = Lists.newArrayList(
        csvHeaderFormat.formatHeader());
    lines.addAll(
        Collections2.transform(
            transactions,
            new Function<Transaction, String>() {
              @Override
              public String apply(
                  @Nullable Transaction transaction
              ) {
                return transactionCsvFormat.format(
                    transaction);
              }
            }));

    return Joiner.on(Conveniences.NEWLINE).join(lines)
        .concat(Conveniences.NEWLINE);
  }
}