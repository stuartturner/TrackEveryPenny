package ca.jbrains.upfp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import ca.jbrains.upfp.domain.ExportTransactionsToCsvAction;
import ca.jbrains.upfp.domain.Transaction;
import ca.jbrains.upfp.domain.TransactionCsvRowFormat;
import com.google.common.collect.Lists;
import org.joda.time.LocalDate;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class BrowseTransactionsActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView transactionsCountView = (TextView) findViewById(R.id.transactionsCount);
        transactionsCountView.setText(String.valueOf(1));
    }

    public void exportTransactionsToCsv(View clicked) {
        final String externalStorageState = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
            notifyUser("There's no external storage available, so I can't export.");
            return;
        }

        final File externalStorageDirectory = Environment.getExternalStorageDirectory();
        final File externalDownloadsDirectory = new File(externalStorageDirectory, Environment.DIRECTORY_DOWNLOADS);
        exportTranasctionsToCsvFileInDirectory(externalDownloadsDirectory);
    }

    // REUSE Android-free
    // REFACTOR Controller implementation
    private void exportTranasctionsToCsvFileInDirectory(File externalDownloadsDirectory) {
        final File transactionsCsvFile = new File(externalDownloadsDirectory, "TrackEveryPenny.csv");
        try {
            final PrintWriter canvas = new PrintWriter(transactionsCsvFile);
            final ArrayList<Transaction> transactions = Lists.newArrayList(
                    new Transaction(new LocalDate(2012, 11, 5), "Bowling Winnings", 200),
                    new Transaction(new LocalDate(2012, 11, 5), "Bowling", -1400),
                    new Transaction(new LocalDate(2012, 11, 7), "Bowling", -1050),
                    new Transaction(new LocalDate(2012, 11, 7),
                            "Bowling Winnings", 100),
                    new Transaction(new LocalDate(2012, 11, 8),
                            "Groceries", -2500),
                    new Transaction(new LocalDate(2012, 11, 10),
                            "Groceries", -11715),
                    new Transaction(new LocalDate(2012, 11, 12),
                            "Bowling", -1400),
                    new Transaction(new LocalDate(2012, 11, 17),
                            "Groceries", -1350)
            );
            writeCsvContentsTo(canvas, transactions);
            canvas.flush();
            canvas.close();
            notifyUser("Exported transactions to " + transactionsCsvFile.getAbsolutePath());
        } catch (IOException logged) {
            notifyUser("I tried to write to external storage, but failed.");
            logError("Failed to write to external public storage", logged);
        }
    }

    // REUSE Domain
    private void writeCsvContentsTo(PrintWriter canvas, Iterable<Transaction> transactions) {
        // SMELL Feels wrong somehow
        // REFACTOR RowFormat goes with HeaderFormat to make CsvFileFormat,
        // then export with TransactionCsvFileFormat?
        new ExportTransactionsToCsvAction(new TransactionCsvRowFormat()).execute(transactions,
                canvas);
    }

    // REUSE App-wide
    private void logError(String errorText, Exception logged) {
        Log.e("TrackEveryPenny", errorText, logged);
    }

    // REUSE App-wide
    private void notifyUser(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public void createTransactionOnCurrentDate(View clicked) {
        notifyUser("Create transaction on current date not yet implemented");
    }
}