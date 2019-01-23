package tk.logiik.vivanfc.viva;

import android.nfc.tech.IsoDep;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import tk.logiik.vivanfc.apdu.APDUInterface;
import tk.logiik.vivanfc.apdu.APDUOperationFailedException;
import tk.logiik.vivanfc.util.BitReader;
import tk.logiik.vivanfc.util.Formats;

public class VivaInterface {

    public static VivaCard fetchData(IsoDep isoDep) throws IOException, APDUOperationFailedException {
        byte[] name, environment, logs, contracts;

        APDUInterface apdu = new APDUInterface(isoDep);
        VivaCard card = new VivaCard();

        apdu.connect();

        // Read name
        apdu.verify("0000");
        apdu.selectById("0003");
        name = apdu.read();
        card.setName(new String(name, 0, name.length - 2, StandardCharsets.ISO_8859_1));

        // Read environment
        apdu.verify("0000");
        apdu.resetSelection();
        apdu.selectByPath("/2000/2001");
        environment = apdu.read();

        BitReader envReader = new BitReader(environment);
        envReader.skip(30);     // Unknown data
        card.setIssuerId(envReader.readInt(8));
        card.setVivaCardId(envReader.readInt(24));
        card.setIssueDate(envReader.readDateDays(14));
        card.setExpirationDate(envReader.readDateDays(14));
        envReader.skip(15);     // Unknown data
        card.setBirthDate(envReader.readDateString(38));

        // Read all logs
        apdu.verify("0000");
        apdu.resetSelection();
        apdu.selectByPath("/2000/2010");
        logs = apdu.readAll();

        BitReader logReader = new BitReader(logs);
        while(logReader.hasNext()) {
            VivaLog log = new VivaLog();

            log.setDate(logReader.readDate(30));
            logReader.skip(38);     // Unknown data (contract signature ?)
            logReader.skip(4);      // Contract used
            logReader.skip(24);     // Unknown data (card pre-set info ?)
            logReader.skip(5);      // Unknown data
            log.setTransitionId(logReader.readInt(3));
            log.setOperatorId(logReader.readInt(5));
            logReader.skip(20);     // Unknown data (vehicle data?)
            logReader.skip(16);     // Reader Id
            logReader.skip(16);     // Line
            logReader.skip(8);      // Station
            logReader.skip(63);     // Unknown data

            card.addLog(log);
        }

        // Read all contracts
        apdu.verify("0000");
        apdu.resetSelection();
        apdu.selectByPath("/2000/2020");
        contracts = apdu.readAll();

        BitReader contractReader = new BitReader(contracts);
        while(contractReader.hasNext()) {
            VivaContract contract = new VivaContract();

            contract.setOperatorId(contractReader.readInt(7));
            contract.setProductId(contractReader.readInt(16));
            contractReader.skip(2);     // Unknown data
            contractReader.skip(14);    // Start date
            contractReader.skip(5);     // Sales point
            contractReader.skip(19);    // Unknown data (sales info?)
            contractReader.skip(16);    // Unknown data (period units?)
            contractReader.skip(14);    // Start/End date
            contractReader.skip(7);     // Validity period
            contractReader.skip(3);     // Unknown data
            contractReader.skip(5);     // Operator 1
            contractReader.skip(4);     // Combined type
            contractReader.skip(11);    // Unknown data
            contractReader.skip(5);     // Operator 2
            contractReader.skip(5);     // Unknown data
            contractReader.skip(5);     // Operator 2
            contractReader.skip(94);    // Unknown data
        }

        apdu.close();

        return card;
    }

}
