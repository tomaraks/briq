package tests.utilitiesTests;

import org.testng.annotations.Test;
import utilities.PDFReader;

import java.io.IOException;

public class PDFExtractionTest {

    @Test
    public void extractBankStatementTest() throws IOException {
        String pdfData = PDFReader.extractTexts("sample statement.PDF");
        String[] pdfDataArray = pdfData.split("\n");

        String bankAddress = "";
        bankAddress = bankAddress + pdfDataArray[0].trim();
        bankAddress = bankAddress + " " + pdfDataArray[1].trim();
        System.out.println("Bank Address is :- " + bankAddress);

        String customerName = "";
        customerName = customerName + pdfDataArray[2];
        System.out.println("Customer Name is :- " + customerName);

        String custAddress = "";
        custAddress = custAddress + pdfDataArray[3].trim();
        custAddress = custAddress + " " +pdfDataArray[4].trim();
        System.out.println("Customer Address is :- " + custAddress);

        String totalChecksPaid = getDataFromLine(pdfDataArray, "Total Checks Paid");
        System.out.println("Total Checks Paid is :- " + totalChecksPaid);
        String totalWithdrawls = getDataFromLine(pdfDataArray, "Total ATM Withdrawals & Debits");
        System.out.println("Total ATM Withdrawals & Debits is :- " + totalWithdrawls);
        String totalDeposit = getDataFromLine(pdfDataArray, "Total Deposits & Other Credits");
        System.out.println("Total Deposits & Other Credits is :- " + totalDeposit);
        String endingBalance = getDataFromLine(pdfDataArray, "Ending Balance on June 5, 2003");
        System.out.println("Ending Balance is :- " + endingBalance);
        String statementDate = getDataFromLine(pdfDataArray, "Statement Date");
        System.out.println("Statement Date is :- " + statementDate);
        String accountNumber = getDataFromLine(pdfDataArray,"Primary Account Number:");
        System.out.println("Account Number is :- " + accountNumber);

    }

    @Test
    public void extractActiveLicencesToExcelTest() throws IOException {
        PDFReader.extractTable("active licences.pdf");
    }

    @Test
    public void extractBidsForTransportToExcelTest() throws IOException {
        PDFReader.extractTable("bids for transport.pdf");
    }

    public String getDataFromLine(String[] alldata, String required) {
        String output = "";
        for(String data: alldata) {
            if(data.contains(required)) {
                if(data.contains("Statement Date")) {
                    String[] out = data.split(":");
                    output = out[out.length-1];
                } else {
                    output = data.replaceAll(required, "");
                }
                output = output.trim();
            }
        }
        return output;
    }
}
