
package com.packt.util.gen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.packt.util.gen package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ExecuteResponse_QNAME = new QName("http://com.packt.wls12c", "executeResponse");
    private final static QName _Execute_QNAME = new QName("http://com.packt.wls12c", "execute");
    private final static QName _ReservationException_QNAME = new QName("http://com.packt.wls12c", "ReservationException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.packt.util.gen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Execute }
     * 
     */
    public Execute createExecute() {
        return new Execute();
    }

    /**
     * Create an instance of {@link Execute.Seats }
     * 
     */
    public Execute.Seats createExecuteSeats() {
        return new Execute.Seats();
    }

    /**
     * Create an instance of {@link ReservationException }
     * 
     */
    public ReservationException createReservationException() {
        return new ReservationException();
    }

    /**
     * Create an instance of {@link ExecuteResponse }
     * 
     */
    public ExecuteResponse createExecuteResponse() {
        return new ExecuteResponse();
    }

    /**
     * Create an instance of {@link Execute.Seats.Entry }
     * 
     */
    public Execute.Seats.Entry createExecuteSeatsEntry() {
        return new Execute.Seats.Entry();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecuteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.packt.wls12c", name = "executeResponse")
    public JAXBElement<ExecuteResponse> createExecuteResponse(ExecuteResponse value) {
        return new JAXBElement<ExecuteResponse>(_ExecuteResponse_QNAME, ExecuteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Execute }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.packt.wls12c", name = "execute")
    public JAXBElement<Execute> createExecute(Execute value) {
        return new JAXBElement<Execute>(_Execute_QNAME, Execute.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReservationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://com.packt.wls12c", name = "ReservationException")
    public JAXBElement<ReservationException> createReservationException(ReservationException value) {
        return new JAXBElement<ReservationException>(_ReservationException_QNAME, ReservationException.class, null, value);
    }

}
