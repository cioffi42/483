import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;


public class MainApplet extends JApplet {

    private static final long serialVersionUID = 1L;
    
    // The width of the DisplayPane relative to the applet width
    private static final double DISPLAY_WIDTH = 0.7;
    
    private static JSplitPane mainPanel;
    public static DisplayPane displayPane;
    public static sidePanel sidePane;
    public static NodeListPanel nodeListPane;
    
    //Contain all nodes and edges from file
    public static Node[] nodes;
    public static Edge[] edges;
    
    @Override
    public void init(){
        initValues();
        displayPane = new DisplayPane();
        JPanel infoPane = new JPanel(new BorderLayout());
        sidePane = new sidePanel();
        nodeListPane = new NodeListPanel();
        
        displayPane.setMinimumSize(new Dimension(400, 400));
        infoPane.setMinimumSize(new Dimension(300, 400));
        
        infoPane.add(sidePane, BorderLayout.CENTER);
        infoPane.add(nodeListPane, BorderLayout.SOUTH);
        
        setSize(1200, 600);
        mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, displayPane, infoPane);
        mainPanel.setOneTouchExpandable(true);
        this.add(mainPanel);
        
        validate();
        mainPanel.setDividerLocation(DISPLAY_WIDTH);
        
        mainPanel.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, 
            new PropertyChangeListener(){
                @Override
                public void propertyChange(PropertyChangeEvent event) {
                    validate();
                    // This code will be executed whenever the slider moves
                    //TestCode.makeTestGraph();
                }
            }
        );
        
        validate();
        TestCode.makeTestGraph();
    }
    
    //Initializes nodes and edges with dummy values.  Used while our database is not yet working.
    public void initValues()
    {
    	int numNodes = 37;
        
        nodes = new Node[numNodes];
        for (int i=0; i<numNodes; i++) nodes[i] = new Node();
        
        //Initialize node names and descriptions
        nodes[0].setName("Weight");
        nodes[0].setDescription("Weight is the force of gravity on an object. The perceived weight can be different (see apparent weight)");
        nodes[1].setName("Position");
        nodes[1].setDescription("Position is the location of an object in space. Strictly speaking it is a three dimensional vector quantity, but it can be reduced to two or one dimensions");
        nodes[2].setName("Velocity");
        nodes[2].setDescription("Velocity is the rate of change of position. Like position, it is a three dimensional vector quantity in general, but in many cases it can be reduced to two or one dimensions.");
        nodes[3].setName("Speed");
        nodes[3].setDescription("Speed is the magnitude of velocity. It is not a vector but a strictly non-negative real number.");
        nodes[4].setName("Acceleration");
        nodes[4].setDescription("Acceleration is the rate of change of velocity, which makes it the second derivative of position. Acceleration, like velocity and position is a three dimensional vector in general, but in many cases, the important physics is only happening in two or one of the dimensions, which allows the quantity to be treated as two or one dimensional, respectively.");
        nodes[5].setName("Force");
        nodes[5].setDescription("Force is a Newtonian concept which captures intuitions about how 'hard' someone or something is pushing or pulling on an object. Its importance and utility for understanding physical situations is established by Newton's Three Laws.");
        nodes[6].setName("Newton's Laws");
        nodes[6].setDescription("Newton's Laws is a set of three laws describing forces that may exist between objects and how these forces dictate the motion of those objects.");
        nodes[7].setName("Newton's First Law");
        nodes[7].setDescription("Newton's First Law states that an object will accelerate if and only if a net force is exerted on it.");
        nodes[8].setName("Newton's Second Law");
        nodes[8].setDescription("Newton's Second Law states that the net force on an object is equal to the object's mass times the resulting acceleration.");
        nodes[9].setName("Newton's Third Law");
        nodes[9].setDescription("Newton's Third Law states that forces always occur in tandem: if an object, A, exerts a force on another object, B, it must be the case that object B exerts a force");
        nodes[10].setName("Angular Position");
        nodes[10].setDescription("Angular position describes extended rigid bodies (objects) and is the angular analogue to position. It is measured in radians, degrees, or revolutions. Strictly speaking it is a three dimensional vector quantity although the directionality is often neglected (properly). The vector points in the direction of the axis of rotation according to the right hand rule, and the magnitude corresponds to the amount by which the object being described has rotated about this axis from its initial orientation.");
        nodes[11].setName("Angular Velocity");
        nodes[11].setDescription("Angular velocity is the rate of change of angular position that describes how fast a rigid body is rotating. It is a three dimensional vector in general directed along the axis of rotation as determined by the right hand rule. The magnitude is of the vector is the rate of rotation in radians per second, degrees per second, rpm, or otherwise. The directionality is often neglected when unimportant.");
        nodes[12].setName("Angular Acceleration");
        nodes[12].setDescription("Angular acceleration is the rate of change of angular velocity. It is directed along the axis of rotation as determined by the right hand rule. It's magnitude is the derivative of angular velocity magnitude. Often the directionality is neglected for simplicity.");
        nodes[13].setName("Torque");
        nodes[13].setDescription("Torque is the angular analogue to force. It is defined with respect to a pivot point, and for any force is equal to the cross product of a vector from the pivot point to the point of application of the force and the force itself (both vector quantities). In the same way that net force is equal to the time derivative of momentum, net torque is equal to the time derivative of angular momentum.");
        nodes[14].setName("Lever Arm");
        nodes[14].setDescription("Lever arm refers to the projection of the displacement vector from a pivot point to the point of application of a force perpendicular to that force.");
        nodes[15].setName("Momentum");
        nodes[15].setDescription("Momentum is the quantity of motion in an object or system of objects. For a single object, momentum is the object's mass times its velocity. For a system, it is the sum of the momenta of all the individual objects.");
        nodes[16].setName("Mass");
        nodes[16].setDescription("Mass can be thought of as the amount of material (or 'stuff') in an object. Alternatively it can be thought of as how resistant the object is to acceleration.");
        nodes[17].setName("Energy");
        nodes[17].setDescription("Energy is an important conserved quantity in physics that can be thought of as the capacity for an object or system of objects to do work. It takes many forms which it can transfer among including kinetic energy, thermal energy and various types of potential energy.");
        nodes[18].setName("Kinetic Energy");
        nodes[18].setDescription("Kinetic energy, E_k is energy in the form of movement. E_k = (1/2)*m*v^2 for a single point object. For a collection of pointlike objects, the total kinetic energy is the sum of kinetic energies for each object in the collection.");
        nodes[19].setName("Work");
        nodes[19].setDescription("Work is a fundamental concept in physics. Work, W, is 'done' when an object is displaced by a vector amount, s, by a force acting in the direction of s (if only partially). The amount of work done is given by W = Integral(F dot ds).");
        nodes[20].setName("Mechanical Energy");
        nodes[20].setDescription("Mechanical Energy is energy in one or more of the following forms: translational kinetic energy, rotational kinetic energy, gravitational potential energy, elastic potential energy or any other form of mechanical potential energy. Thermal energy, electrical energy and chemical energy are specifically excluded.");
        nodes[21].setName("Rotational Kinetic Energy");
        nodes[21].setDescription("Energy in the form of rotational movement. E_Kr = (1/2)*I*/omega^2");
        nodes[22].setName("Translational Kinetic Energy");
        nodes[22].setDescription("Translational kinetic energy refers to the part of a body's or system's kinetic energy that is due to its center of mass motion, not rotation about that center of mass, for example. ");
        nodes[23].setName("Angular Momentum");
        nodes[23].setDescription("Angular momentum is the quantity of angular motion about a pivot point. For a single pointlike object, the angular momentum is the cross product of the object's displacement from the pivot point with the object's velocity. For a system of pointlike objects, angular momentum is the sum of individual angular momenta. For rigid bodies possessing certain types of symmetry, angular momentum is equal to rotational inertia times angular velocity.");
        nodes[24].setName("Rotational Inertia");
        nodes[24].setDescription("Rotational inertia is a scalar resistance to angular acceleration related to mass exhibited by certain symmetrical rigid bodies. For such bodies, it is equal to the sum of mass times distance to rotational axis squared for each mass element in the body. The rotational inertia about one axis will in general be different from that about another axis for the same object.");
        nodes[25].setName("Normal Force");
        nodes[25].setDescription("Normal force is the contact force on an object present at its physical (surface) interface with another object and directed perpendicular to the interface. The force is always directed away from the interface.");
        nodes[26].setName("Elastic Force");
        nodes[26].setDescription("An elastic force is a force that does not do work.");
        nodes[27].setName("Friction");
        nodes[27].setDescription("Friction is a force that opposes motion. The force is dissipitative in that it causes mechanical energy to be converted into some other form.");
        nodes[28].setName("Sliding Friction");
        nodes[28].setDescription("Sliding friction is a form of friction that occurs at a surface interface between two objects. It opposes relative motion between the two surfaces.");
        nodes[29].setName("Static Friction");
        nodes[29].setDescription("Static friction is a kind of sliding friction that acts to prevent relative motion between two surfaces from taking place. By definition, static friction is only present when two surfaces are not moving with respect to one another.");
        nodes[30].setName("Kinetic Friction");
        nodes[30].setDescription("Kinetic friction is a kind of sliding friction that acts to resist relative motion between two surfaes that is already taking place. By definition, kinetic friction is only present when two surfaces are moving with respect to one another.");
        nodes[31].setName("Coefficient of Friction");
        nodes[31].setDescription("A coefficient of friction relates a frictional force to the normal force through a simple proportionality factor. There are two important coefficients of friction for materials: the coefficient of static friction and the coefficient of kinetic friction.");
        nodes[32].setName("Coefficient of Static Friction");
        nodes[32].setDescription("The coefficient of static friction is equal to the ratio of static friction to normal force for objects in contact with a material.");
        nodes[33].setName("Coefficient of Kinetic Friction");
        nodes[33].setDescription("The coefficient of kinetic friction is equal to the ratio of kinetic friction to normal force for objects in contact with a material.");
        nodes[34].setName("Gravitational Force");
        nodes[34].setDescription("The force, due to gravity, exerted by massive objects on each other. Often, the earth is properly treated as the only appreciable contributor to the gravitational force on an object.");
        nodes[35].setName("Impulse");
        nodes[35].setDescription("Impulse measures the effect of a force over time on an object. For constant forces, it is defined as force times time of application. In general, it is the integral of force by time. Net impulse yields the change in momentum for an object.");
        nodes[36].setName("Inverse Square Law");
        nodes[36].setDescription("The inverse square law is the most general and fundamental description of gravitational forces encountered in Physics 211. It describes the gravitational force acting between two massive objects as a function of their masses and the distance between them. It reads, F_G = Gm_1 m_2/r^2.");
        Edge[] newEdges = {me(0,1), me(0,2), me(0,3), me(0,4), me(0,5), me(0,6), me(0,7), me(0,8), me(0,9), me(0,10), me(0,11), me(0,12), me(0,13), me(0,14), me(1,2)};
        edges = newEdges;
    }
    
    // Make Edge
    private static Edge me(int i, int j){
        return new Edge(1.0, nodes[i], nodes[j]);
    }
}
