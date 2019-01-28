package tk.logiik.vivanfc.viva;

import android.nfc.tech.IsoDep;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import tk.logiik.vivanfc.apdu.APDUInterface;
import tk.logiik.vivanfc.apdu.APDUOperationFailedException;
import tk.logiik.vivanfc.util.BitReader;
import tk.logiik.vivanfc.viva.values.VivaValues;

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
        while (logReader.hasNext()) {
            VivaLog log = new VivaLog();

            Date date       = logReader.readDate(30);
                              logReader.skip(38);       // Unknown data (contract signature ?)
            int contractId  = logReader.readInt(4);
                              logReader.skip(24);       // Unknown data (card pre-set info ?)
                              logReader.skip(5);        // Unknown data
            int transitionId= logReader.readInt(3);
            int operatorId  = logReader.readInt(5);
                              logReader.skip(20);       // Unknown data (vehicle data?)
            int readerId    = logReader.readInt(16);
            int lineId      = logReader.readInt(16);
            int stationId;
            if (operatorId == VivaValues.OPERATOR_ML) {
                stationId   = logReader.readInt(6);
                              logReader.skip(2);
            } else {
                stationId   = logReader.readInt(8);
            }
                              logReader.skip(63);

            log.setDate(date);
            log.setContractId(contractId);
            log.setTransitionId(transitionId);
            log.setOperatorId(operatorId);
            log.setReaderId(readerId);
            log.setLineId(lineId);
            log.setStationId(stationId);

            card.addLog(log);
        }

        // Read all contracts
        apdu.verify("0000");
        apdu.resetSelection();
        apdu.selectByPath("/2000/2020");
        contracts = apdu.readAll();

        BitReader contractReader = new BitReader(contracts);
        while (contractReader.hasNext()) {
            VivaContract contract = new VivaContract();

            int operatorId      = contractReader.readInt(7);
            if (operatorId == 0) {
                contractReader.skip(225);
                continue;
            }
            int productId       = contractReader.readInt(16);
                                  contractReader.skip(2);       // Unknown data
            Date startDate      = contractReader.readDateDays(14);
            int pointOfSaleId   = contractReader.readInt(5);
                                  contractReader.skip(19);      // Unknown data (sales info?)
            int unitsId         = contractReader.readInt(16);
                                  contractReader.skip(14);      // Unknown data (start/end date)
            int validity        = contractReader.readInt(7);
                                  contractReader.skip(3);       // Unknown data
                                  contractReader.skip(5);       // Operator 1
                                  contractReader.skip(4);       // Combined type
                                  contractReader.skip(11);      // Unknown data
                                  contractReader.skip(5);       // Operator 2
                                  contractReader.skip(5);       // Unknown data
                                  contractReader.skip(5);       // Operator 2
                                  contractReader.skip(94);      // Unknown data

            contract.setOperatorId(operatorId);
            contract.setProductId(productId);
            contract.setStartDate(startDate);
            contract.setPointOfSaleId(pointOfSaleId);
            contract.setEndDate(startDate, unitsId, validity);

            card.addContract(contract);
        }

        apdu.close();

        return card;
    }

}
