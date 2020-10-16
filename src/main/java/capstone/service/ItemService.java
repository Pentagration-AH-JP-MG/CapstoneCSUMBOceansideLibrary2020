package capstone.service;

import capstone.domain.CheckinInfo;
import capstone.domain.CheckoutInfo;
import capstone.domain.SearchHold;
import capstone.domain.SearchItem;
import com.ceridwen.circulation.SIP.exceptions.ChecksumError;
import com.ceridwen.circulation.SIP.exceptions.InvalidFieldLength;
import com.ceridwen.circulation.SIP.exceptions.MandatoryFieldOmitted;
import com.ceridwen.circulation.SIP.exceptions.MessageNotUnderstood;
import com.ceridwen.circulation.SIP.exceptions.RetriesExceeded;
import com.ceridwen.circulation.SIP.exceptions.SequenceError;
import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.CheckIn;
import com.ceridwen.circulation.SIP.messages.CheckInResponse;
import com.ceridwen.circulation.SIP.messages.CheckOut;
import com.ceridwen.circulation.SIP.messages.CheckOutResponse;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.messages.SCStatus;
import com.ceridwen.circulation.SIP.netty.server.SIPDaemon;
import com.ceridwen.circulation.SIP.samples.netty.DummyDriverFactory;
import com.ceridwen.circulation.SIP.transport.SSLSocketConnection;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.ceridwen.circulation.SIP.types.enumerations.ProtocolVersion;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import io.netty.handler.ssl.util.SelfSignedCertificate;

@Service
public class ItemService {

  private static SelfSignedCertificate ssc;
  private static final boolean SSL = false;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  // to get the bib of a particular item
  public SearchItem checkBarcode(String barcode) {

    // debugging
    System.out.println("In checkBarcode here barcode passed is " + barcode);

    String sql = "SELECT \n"
        + "\tbirl.bib_record_id AS bib,\n"
        + "\ti.id AS item,\n"
        + "\ti.record_num AS recordNum\n"
        + "FROM\n"
        + "\tsierra_view.item_view i\n"
        + "JOIN\n"
        + "\tsierra_view.bib_record_item_record_link birl\n"
        + "\tON\n"
        + "\ti.id = birl.item_record_id\n"
        + "WHERE\n"
        + "\ti.barcode = ?";

    try {
    SearchItem answer = jdbcTemplate.queryForObject(sql, new Object[]{barcode},new BeanPropertyRowMapper<>(SearchItem.class));

    return answer;}
    catch (EmptyResultDataAccessException e) {
      return null;
    }
  }

  public List<SearchItem> getMissingItemList(String location, String bib){
    System.out.println("In checkBarcode here location code passed is " + location + " barcode passed is " + bib);
    BigInteger birl = new BigInteger(bib);
    System.out.println(birl.toString());
    String sql = "SELECT \n"
            +"\tbirl.bib_record_id AS bib,\n"
            +"\ti.id AS item,\n"
            +"\ti.record_num AS recordNum \n"
            +"FROM\n"
            +"\tsierra_view.item_view i\n"
            +"JOIN\n"
            +"\tsierra_view.bib_record_item_record_link birl\n"
            +"\tON\n"
            +"\ti.id = birl.item_record_id\n"
            +"WHERE\n"
            +"\t i.item_status_code = \'-\'\n"
            +"\t AND LEFT(i.location_code, 1) = ?\n"
            +"\t AND birl.bib_record_id = ?";

    return jdbcTemplate.query(sql, new Object[]{birl},new BeanPropertyRowMapper<>(SearchItem.class));
  }

  public SearchItem getMissingItem(String bib){
    System.out.println("In checkBarcode here barcode passed is " + bib);
    BigInteger birl = new BigInteger(bib);
    System.out.println(birl.toString());
    String sql = "SELECT \n"
        +"\tbirl.bib_record_id AS bib,\n"
        +"\ti.id AS item,\n"
        +"\ti.record_num AS recordNum \n"
        +"FROM\n"
        +"\tsierra_view.item_view i\n"
        +"JOIN\n"
        +"\tsierra_view.bib_record_item_record_link birl\n"
        +"\tON\n"
        +"\ti.id = birl.item_record_id\n"
        +"WHERE\n"
        +"\t i.item_status_code = \'-\'\n"
        +"\tAND birl.item_record_id = ?";

    return jdbcTemplate.queryForObject(sql, new Object[]{birl},new BeanPropertyRowMapper<>(SearchItem.class));
  }

  public SearchHold getNewHold(BigInteger patron){
    System.out.println("In getNewHold here patron passed is " + patron);

    String sql = "SELECT MAX(id) AS hold from sierra_view.hold\n"
        + "WHERE patron_record_id = ?";

    return jdbcTemplate.queryForObject(sql, new Object[]{patron},new BeanPropertyRowMapper<>(SearchHold.class));
  }

  public boolean sipCheckIn (CheckinInfo info) throws Exception {
    System.out.println("in sipCheckIn");

    try {
      System.setProperty("com.ceridwen.circulation.SIP.charset", "ISO8859_1");

      SIPDaemon server;

      // Run netty server
      if (SSL) {
        ssc = new SelfSignedCertificate();
        server = new SIPDaemon("Sample", "localhost", 5060, ssc.certificate(), ssc.privateKey(), new DummyDriverFactory(), true);
      } else {
        server = new SIPDaemon("Sample", "localhost", 5060, new DummyDriverFactory(), true);
      }
      System.out.println("server.start()");
      server.start();

      // CheckIn
      System.out.println("SIP CheckIn");

      // create socket and connect
      SocketConnection connection;

      if (SSL) {
        connection = new SSLSocketConnection();
        ((SSLSocketConnection) connection).setServerCertificateCA(ssc.certificate());
      } else {
        connection = new SocketConnection();
      }
      connection.setHost("hostinfo here");
      connection.setPort("host port here");
      connection.setConnectionTimeout(30000);
      connection.setIdleTimeout(30000);
      connection.setRetryAttempts(2);
      connection.setRetryWait(500);

      try {
        connection.connect();
      } catch (Exception ex) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
        return false;
      }

      /**
       * It is necessary to send a SC Status with protocol version 2.0 else server
       * will assume 1.0)
       */
      SCStatus scStatusRequest = new SCStatus();
      scStatusRequest.setProtocolVersion(ProtocolVersion.VERSION_2_00);

      Message scStatusResponse;

      try {
        scStatusResponse = connection.send(scStatusRequest);
      } catch (RetriesExceeded | MessageNotUnderstood | ChecksumError | SequenceError | MandatoryFieldOmitted | InvalidFieldLength ex) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
        return false;
      }

      if (!(scStatusResponse instanceof ACSStatus)) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, "Error - Status Request did not return valid response from server.");
        return false;
      }

      /**
       * For debugging XML handling code (but could be useful in Cocoon)
       */
      scStatusResponse.xmlEncode(System.out);

      /**
       * Check if the server can support checkin
       */
      if (!((ACSStatus) scStatusResponse).getSupportedMessages().isCheckIn()) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, "Check out not supported");
        return false;
      }

      CheckIn checkInRequest = new CheckIn();

      /**
       * Now try a checkin request
       * SIP protocol specifies only these 4 as required
       */
      checkInRequest.setItemIdentifier(info.getItemBarcode());
      checkInRequest.setCurrentLocation(info.getFillLocation());
      checkInRequest.setInstitutionId("");
      checkInRequest.setTerminalPassword("");
      // need to know if we need to set field AY, doesn't map to anything here that I can see if so

      Message checkInResponse;

      try {
        checkInResponse = connection.send(checkInRequest);
      } catch (RetriesExceeded | MessageNotUnderstood | ChecksumError | SequenceError | MandatoryFieldOmitted | InvalidFieldLength ex) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
        return false;
      }

      if (!(checkInResponse instanceof CheckInResponse)) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, "Error - CheckIn Request did not return valid response from server");
        return false;
      }

      connection.disconnect();
      // Shut down netty server
      System.out.println("server.stop()");
      server.stop();
      checkInResponse.xmlEncode(System.out);
      if(((CheckInResponse) checkInResponse).getScreenMessage().equals("Item was put on holdshelf."))
        return true;
    } catch (Exception ex) {
      Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }

  public boolean sipCheckOut (CheckoutInfo barcodes) throws Exception {
    System.out.println("in sipCheckOut");

    try {
      System.setProperty("com.ceridwen.circulation.SIP.charset", "ISO8859_1");

      SIPDaemon server;

      // Run netty server
      if (SSL) {
        ssc = new SelfSignedCertificate();
        server = new SIPDaemon("Sample", "localhost", 5060, ssc.certificate(), ssc.privateKey(), new DummyDriverFactory(), true);
      } else {
        server = new SIPDaemon("Sample", "localhost", 5060, new DummyDriverFactory(), true);
      }
      System.out.println("server.start()");
      server.start();

      // CheckOut
      System.out.println("SIP Checkout");

      // create socket and connect
      SocketConnection connection;

      if (SSL) {
        connection = new SSLSocketConnection();
        ((SSLSocketConnection) connection).setServerCertificateCA(ssc.certificate());
      } else {
        connection = new SocketConnection();
      }
      connection.setHost("hostinfo here");
      connection.setPort("host port here");
      connection.setConnectionTimeout(30000);
      connection.setIdleTimeout(30000);
      connection.setRetryAttempts(2);
      connection.setRetryWait(500);

      try {
        connection.connect();
      } catch (Exception ex) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
        return false;
      }

      /**
       * It is necessary to send a SC Status with protocol version 2.0 else server
       * will assume 1.0)
       */
      SCStatus scStatusRequest = new SCStatus();
      scStatusRequest.setProtocolVersion(ProtocolVersion.VERSION_2_00);

      Message scStatusResponse;

      try {
        scStatusResponse = connection.send(scStatusRequest);
      } catch (RetriesExceeded | MessageNotUnderstood | ChecksumError | SequenceError | MandatoryFieldOmitted | InvalidFieldLength ex) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
        return false;
      }

      if (!(scStatusResponse instanceof ACSStatus)) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, "Error - Status Request did not return valid response from server.");
        return false;
      }

      /**
       * For debugging XML handling code (but could be useful in Cocoon)
       */
      scStatusResponse.xmlEncode(System.out);

      /**
       * Check if the server can support checkin
       */
      if (!((ACSStatus) scStatusResponse).getSupportedMessages().isCheckOut()) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, "Check out not supported");
        return false;
      }

      CheckOut checkOutRequest = new CheckOut();

      /**
       * Now try a checkout request
       * SIP protocol specifies only these 4 as required
       */
      System.out.println("in checkout, patron identifier is " + barcodes.getPatronBarcode());
      System.out.println("in checkout, item identifier is " + barcodes.getItemBarcode());
      checkOutRequest.setPatronIdentifier(barcodes.getPatronBarcode());
      checkOutRequest.setItemIdentifier(barcodes.getItemBarcode());
      checkOutRequest.setSCRenewalPolicy(Boolean.TRUE);
      checkOutRequest.setTransactionDate(new Date());
      // need to know if we need to set field AY, doesn't map to anything here that I can see if so

      Message checkOutResponse;

      try {
        checkOutResponse = connection.send(checkOutRequest);
      } catch (RetriesExceeded | MessageNotUnderstood | ChecksumError | SequenceError | MandatoryFieldOmitted | InvalidFieldLength ex) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
        return false;
      }

      if (!(checkOutResponse instanceof CheckOutResponse)) {
        Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, "Error - CheckOut Request did not return valid response from server");
        return false;
      }

      connection.disconnect();
      // Shut down netty server
      System.out.println("server.stop()");
      server.stop();
      checkOutResponse.xmlEncode(System.out);
    } catch (Exception ex) {
      Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
    }
      return true;
  }

}