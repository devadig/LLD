// ============================================================
//  STRUCTURAL PATTERNS — Complete Interview Guide
// ============================================================
//
//  7 patterns that deal with object composition and relationships.
//  They simplify structure by identifying relationships between entities.
//
// ============================================================


// ============================================================
//  ADAPTER — "Make incompatible interfaces work together"
// ============================================================
//
//  DEFINITION: Converts the interface of a class into another interface
//              clients expect. Lets classes work together that couldn't
//              otherwise because of incompatible interfaces.
//
//  REAL-WORLD ANALOGY:
//  Power socket adapter when traveling
//  → Your Indian plug (2 round pins) doesn't fit US socket (2 flat pins)
//  → Adapter wraps your plug and exposes US socket interface
//  → Your device works without modification
//
//  THE PROBLEM IT SOLVES:
//  → You have third-party library/legacy code with interface X
//  → Your system expects interface Y
//  → You can't modify the third-party code
//  → Without Adapter: Rewrite entire codebase to use X
//  → With Adapter: Create wrapper that translates X → Y
//
//  WHEN TO USE:
//  → Integrating third-party libraries
//  → Working with legacy code
//  → Making two existing interfaces compatible
//  → You want to use existing class but interface doesn't match
//
//  TYPES:
//  1. OBJECT ADAPTER (composition) — Recommended in Java
//     → Adapter HAS-A adaptee (uses composition)
//     → More flexible
//  
//  2. CLASS ADAPTER (multiple inheritance)
//     → Adapter IS-A adaptee AND IS-A target
//     → Not possible in Java (no multiple class inheritance)
//
//  KEY PARTICIPANTS:
//  → Target: Interface your code expects (PaymentGateway)
//  → Adaptee: Existing incompatible class (PayPalPayment)
//  → Adapter: Wrapper that makes Adaptee work with Target
//  → Client: Uses Target interface
//
//  BENEFITS:
//  → Reuse existing code without modification
//  → Decouple client from third-party implementation
//  → Single Responsibility: Translation logic isolated
//  → Open/Closed: Add new adapters without changing client
//
//  INTERVIEW ANSWER:
//  "Adapter makes incompatible interfaces work together. For example,
//  my system expects PaymentGateway interface with processPayment(),
//  but PayPal library has sendPayment(). I create PayPalAdapter
//  that implements PaymentGateway and wraps PayPal, translating
//  processPayment() calls to sendPayment(). This lets me use PayPal
//  without modifying my system or PayPal's code. For 3 payment
//  providers, I create 3 adapters — each follows SRP (one provider,
//  one adapter) and OCP (add new provider = new adapter class)."


// ============================================================
//  BRIDGE — "Decouple abstraction from implementation"
// ============================================================
//
//  DEFINITION: Separates an abstraction from its implementation so
//              both can vary independently. Prevents class explosion
//              from multiple dimensions of variation.
//
//  REAL-WORLD ANALOGY:
//  TV and Remote Control
//  → Remote (abstraction) controls TV (implementation)
//  → Different remotes: Basic, Advanced
//  → Different TVs: Sony, Samsung
//  → Without Bridge: SonyBasic, SonyAdvanced, SamsungBasic, SamsungAdvanced (4 classes)
//  → With Bridge: 2 remotes + 2 TVs = 4 classes, but scales better
//  → Add LG TV: +1 class (not +2)
//
//  THE PROBLEM IT SOLVES:
//  → Two dimensions that vary independently create N×M class explosion
//  → Example: N shapes × M colors = N×M classes
//  → Bridge: N shapes + M colors = N+M classes
//
//  TERRIBLE JARGON TRANSLATION:
//  → "Abstraction" = WHAT you're doing (shape, notification priority)
//  → "Implementation" = HOW you're doing it (rendering API, message channel)
//  → "Decouple" = They can change independently
//
//  WHEN TO USE:
//  → Two or more dimensions vary independently
//  → Want to avoid class explosion from combinations
//  → Both abstraction and implementation should be extensible
//  → Share implementation among multiple objects
//
//  STRUCTURE:
//  → ABSTRACTION hierarchy (Shape, Notification)
//  → IMPLEMENTATION hierarchy (RenderingAPI, MessageSender)
//  → Bridge = composition field connecting them
//
//  KEY PARTICIPANTS:
//  → Abstraction: High-level control (Shape)
//  → Refined Abstraction: Specific types (Circle, Rectangle)
//  → Implementor: Interface for implementations (RenderingAPI)
//  → Concrete Implementor: Actual implementations (OpenGL, DirectX)
//
//  BENEFITS:
//  → Prevents combinatorial explosion
//  → Decouples interface from implementation
//  → Both hierarchies can extend independently
//  → Hide implementation from client
//
//  BRIDGE vs ADAPTER:
//  → ADAPTER: Fixes incompatibility (reactive, two existing things)
//  → BRIDGE: Designed upfront to separate concerns (proactive, planning)
//
//  BRIDGE vs STRATEGY:
//  → STRATEGY: One dimension varies (different algorithms)
//  → BRIDGE: Two dimensions vary (shape AND color)
//
//  INTERVIEW ANSWER:
//  "Bridge separates two independent dimensions to prevent class
//  explosion. For example, shapes (Circle, Rectangle) and rendering
//  APIs (OpenGL, DirectX). Without Bridge: 3 shapes × 3 APIs = 9 classes.
//  With Bridge: Shape hierarchy + RenderingAPI hierarchy connected
//  via composition = 6 classes. Add new shape? +1 class. Add new API?
//  +1 class. They vary independently. The 'bridge' is the composition
//  field (protected RenderingAPI renderer) that connects them."


// ============================================================
//  COMPOSITE — "Treat individual and groups uniformly"
// ============================================================
//
//  DEFINITION: Composes objects into tree structures to represent
//              part-whole hierarchies. Lets clients treat individual
//              objects and compositions uniformly.
//
//  REAL-WORLD ANALOGY:
//  File System
//  → File (leaf): Individual item, has size
//  → Folder (composite): Contains files and folders, has total size
//  → You call getSize() on both — file returns its size, folder sums children
//  → Same interface, works uniformly
//
//  THE PROBLEM IT SOLVES:
//  → Without Composite: if-else everywhere to check "is it a file or folder?"
//  → With Composite: Just call component.operation() — works for both
//  → Client code is simple, uniform
//
//  WHEN TO USE:
//  → Tree structures (file system, UI widgets, org charts)
//  → Part-whole hierarchies
//  → Want to treat leaves and composites uniformly
//  → Operations should work recursively through structure
//
//  STRUCTURE:
//  → Component: Interface (FileSystemComponent)
//  → Leaf: Individual object (File) — has no children
//  → Composite: Container (Folder) — has children, delegates to them
//
//  KEY PARTICIPANTS:
//  → Component: Common interface (showDetails, getSize)
//  → Leaf: End object, implements operations directly (File)
//  → Composite: Has children, implements operations recursively (Folder)
//  → Client: Works with Component interface
//
//  HOW OPERATIONS WORK:
//  → LEAF: Simple, non-recursive (base case)
//     File.getSize() → return size
//  → COMPOSITE: Recursive, delegates to children
//     Folder.getSize() → sum of all children's getSize()
//
//  BENEFITS:
//  → Uniform treatment (no type checking)
//  → Easy to add new components
//  → Simplifies client code
//  → Recursive structure naturally handled
//
//  TRADE-OFFS:
//  → Can make design overly general
//  → Hard to restrict child types (folder can contain anything)
//
//  INTERVIEW ANSWER:
//  "Composite treats individual objects and groups uniformly via tree
//  structures. For example, File and Folder both implement
//  FileSystemComponent. File.getSize() returns its size. Folder.getSize()
//  recursively sums children's sizes. Client just calls component.getSize()
//  — doesn't need if-else to check type. Add search()? File checks name,
//  Folder checks name then recursively searches children. This simplifies
//  client code and enables natural recursion through hierarchies."


// ============================================================
//  DECORATOR — "Add responsibilities dynamically"
// ============================================================
//
//  DEFINITION: Attaches additional responsibilities to an object
//              dynamically. Provides flexible alternative to subclassing
//              for extending functionality.
//
//  REAL-WORLD ANALOGY:
//  Coffee Shop Toppings
//  → Plain coffee ₹50
//  → Add milk (+₹20), sugar (+₹10), caramel (+₹30)
//  → Instead of 8 classes (PlainCoffee, MilkCoffee, MilkSugarCoffee...)
//  → Wrap: Caramel(Sugar(Milk(Coffee()))) — each wrapper adds cost
//
//  THE PROBLEM IT SOLVES:
//  → N features → 2^N subclasses (combinatorial explosion)
//  → Can't add features at runtime with inheritance
//  → Decorator: N decorator classes, infinite runtime combinations
//
//  HOW IT WORKS (The Magic):
//  → Each decorator IS-A Component (same interface)
//  → Each decorator HAS-A Component (wraps it)
//  → Each decorator ADDS behavior (before/after delegation)
//  → Decorators stack like Russian nesting dolls
//
//  CALL CHAIN (getCost example):
//  Pepperoni.getCost() → Olives.getCost() + 60
//    → Cheese.getCost() + 30
//      → Plain.getCost() + 50
//        → 100 (base)
//  Result: 100 + 50 + 30 + 60 = 240
//
//  WHEN TO USE:
//  → Add responsibilities to objects without subclassing
//  → Responsibilities can be withdrawn
//  → Combination of features needed
//  → Extension by subclassing is impractical (too many combos)
//
//  STRUCTURE:
//  → Component: Interface (Coffee)
//  → ConcreteComponent: Object being decorated (PlainCoffee)
//  → Decorator: Base decorator, wraps Component (CoffeeDecorator)
//  → ConcreteDecorator: Adds specific behavior (MilkDecorator)
//
//  KEY PARTICIPANTS:
//  → Component: Common interface (getDescription, getCost)
//  → ConcreteComponent: Core object (PlainCoffee)
//  → Decorator: Wraps Component, delegates + adds behavior
//  → ConcreteDecorator: Specific additions (MilkDecorator, SugarDecorator)
//
//  BENEFITS:
//  → More flexible than inheritance
//  → Avoid feature-laden classes high in hierarchy
//  → Add/remove responsibilities at runtime
//  → Follows OCP (new features = new decorators)
//  → Single Responsibility (each decorator has one job)
//
//  DECORATOR vs ADAPTER:
//  → ADAPTER: Changes interface (makes incompatible things compatible)
//  → DECORATOR: Keeps same interface, adds behavior
//
//  DECORATOR vs PROXY:
//  → DECORATOR: Adds new functionality
//  → PROXY: Controls access (same functionality)
//
//  REAL JAVA EXAMPLE:
//  BufferedReader(FileReader(file)) — I/O streams use Decorator!
//  → FileReader: reads bytes
//  → BufferedReader: adds buffering
//  → LineNumberReader: adds line counting
//
//  INTERVIEW ANSWER:
//  "Decorator adds responsibilities dynamically without modifying the
//  class. Instead of subclassing for every combination, I wrap objects.
//  Each wrapper has the same interface, wraps another object, and adds
//  behavior. For example, pizza toppings: CheeseDecorator wraps PlainPizza,
//  OlivesDecorator wraps that result. When I call getCost(), it flows
//  through all wrappers recursively, each adding its cost. This follows
//  OCP — new toppings are new classes, not edits — and allows runtime
//  combinations like double cheese. Java's I/O streams use this:
//  BufferedReader wraps FileReader to add buffering."


// ============================================================
//  FACADE — "Provide simplified interface to subsystem"
// ============================================================
//
//  DEFINITION: Provides a unified, simplified interface to a set of
//              interfaces in a subsystem. Makes subsystem easier to use.
//
//  REAL-WORLD ANALOGY:
//  Hotel Concierge
//  → Without: Call restaurant, spa, laundry, taxi separately
//  → With: Call concierge once — they coordinate everything
//  → Services still exist independently, but one simple entry point
//
//  THE PROBLEM IT SOLVES:
//  → Complex subsystem has 10 classes, 50 methods
//  → Client needs to call 8 methods in correct order for one task
//  → Facade wraps this into facade.doTask() — one call, clean API
//
//  WHEN TO USE:
//  → Want simple interface to complex subsystem
//  → Decouple clients from subsystem
//  → Layer architecture (Service layer is facade over Repository)
//  → Reduce dependencies between subsystems
//
//  STRUCTURE:
//  → Facade: Simple interface, coordinates subsystem
//  → Subsystem classes: Complex classes doing actual work
//  → Client: Uses Facade, not subsystem directly
//
//  KEY POINTS:
//  → Doesn't HIDE subsystem (still accessible if needed)
//  → Just offers CONVENIENT higher-level API
//  → One-way dependency: Facade → Subsystem (not reverse)
//
//  BENEFITS:
//  → Shields clients from subsystem complexity
//  → Promotes weak coupling
//  → Easier to use, learn, and test
//  → Subsystem changes isolated in Facade
//
//  FACADE vs ADAPTER:
//  → ADAPTER: Makes incompatible interfaces compatible (two things)
//  → FACADE: Simplifies complex subsystem (one thing made easier)
//
//  FACADE vs MEDIATOR:
//  → FACADE: Unidirectional (client → facade → subsystem)
//  → MEDIATOR: Bidirectional (colleagues talk via mediator)
//
//  INTERVIEW ANSWER:
//  "Facade provides a simplified interface to a complex subsystem.
//  For example, starting a computer requires CPU.freeze(), Memory.load(),
//  HardDrive.read(), CPU.execute() in order. ComputerFacade wraps this
//  into computer.start() — one call. Client code is simple. If subsystem
//  changes (e.g., HardDrive now needs spinUp() before read()), only
//  Facade changes, not client code. This decouples client from subsystem
//  and simplifies usage. It's like a hotel concierge — one point of
//  contact for many services."


// ============================================================
//  FLYWEIGHT — "Share common state to save memory"
// ============================================================
//
//  DEFINITION: Uses sharing to support large numbers of fine-grained
//              objects efficiently. Minimizes memory by sharing common
//              state (intrinsic) and externalizing unique state (extrinsic).
//
//  REAL-WORLD ANALOGY:
//  Text Editor Characters
//  → Rendering "Hello" doesn't create 5 separate 'l' objects
//  → Character 'l' (shape, font) is SHARED (intrinsic)
//  → Position (x, y) is UNIQUE (extrinsic, passed when rendering)
//  → One 'l' object used twice at different positions
//
//  THE PROBLEM IT SOLVES:
//  → Creating millions of objects with mostly shared data
//  → Example: 1 million trees in forest game
//  → Without Flyweight: 1M tree objects (each has texture, position)
//  → With Flyweight: 10 TreeType objects (shared) + 1M positions (extrinsic)
//  → Memory: 1M objects → 10 objects + 1M small positions
//
//  KEY CONCEPTS:
//  → INTRINSIC STATE: Shared, immutable, stored in Flyweight
//     Example: Tree type, texture, color
//  → EXTRINSIC STATE: Unique per instance, stored outside, passed at runtime
//     Example: Tree position (x, y)
//  → FACTORY: Manages Flyweight pool, ensures sharing
//
//  WHEN TO USE:
//  → Application uses large number of objects
//  → Storage costs are high
//  → Most object state can be made extrinsic
//  → Objects can be replaced by fewer shared objects
//  → Application doesn't depend on object identity
//
//  STRUCTURE:
//  → Flyweight: Shared object with intrinsic state (TreeType)
//  → FlyweightFactory: Creates and manages Flyweights (TreeFactory)
//  → Client: Stores extrinsic state, passes it to Flyweight (Tree)
//
//  KEY PARTICIPANTS:
//  → Flyweight: Stores intrinsic state (TreeType: name, texture)
//  → ConcreteFlyweight: Implements Flyweight (specific tree type)
//  → FlyweightFactory: Creates/shares Flyweights (TreeFactory)
//  → Client: Stores extrinsic state (Tree: x, y, references TreeType)
//
//  BENEFITS:
//  → Drastically reduces memory usage
//  → Fewer objects → better performance
//  → Sharing → less duplication
//
//  TRADE-OFFS:
//  → Complexity in separating intrinsic/extrinsic state
//  → Runtime cost of passing extrinsic state
//  → Factory overhead
//
//  INTERVIEW ANSWER:
//  "Flyweight minimizes memory by sharing common state across many objects.
//  For example, 1 million trees in a game. Each tree has type (oak, pine)
//  and position. Without Flyweight: 1M objects. With Flyweight: intrinsic
//  state (tree type, texture) is shared in TreeType objects, extrinsic
//  state (x, y position) is stored in Tree objects. TreeFactory ensures
//  only one TreeType per type. Result: 1M oaks share ONE Oak TreeType
//  object. Memory saved massively. Java String pool uses this — 'hello'
//  literal reused, not recreated."


// ============================================================
//  PROXY — "Control access to an object"
// ============================================================
//
//  DEFINITION: Provides a surrogate or placeholder for another object
//              to control access to it. Has same interface as real object
//              but adds control layer.
//
//  REAL-WORLD ANALOGY:
//  Lawyer in Negotiation
//  → Instead of you negotiating directly, your lawyer (proxy) does it
//  → Same interface (negotiation), but lawyer adds expertise, checks
//  → Other party doesn't know they're not talking to you directly
//
//  THE PROBLEM IT SOLVES:
//  → Want to add control (lazy loading, access control, logging)
//  → Don't want to modify real object
//  → Real object is expensive to create/access
//
//  TYPES OF PROXIES:
//  1. VIRTUAL PROXY: Lazy loading expensive objects
//     → Create real object only when needed
//     → Example: Load large image only when display() called
//  
//  2. PROTECTION PROXY: Access control
//     → Check permissions before delegating
//     → Example: Only ADMIN can view sensitive images
//  
//  3. REMOTE PROXY: Represents object in different address space
//     → Example: RMI, REST API calls
//  
//  4. CACHING PROXY: Cache results from real object
//     → Avoid expensive recomputation
//
//  WHEN TO USE:
//  → Need lazy initialization (virtual proxy)
//  → Access control required (protection proxy)
//  → Remote object access (remote proxy)
//  → Logging, reference counting, caching needed
//
//  STRUCTURE:
//  → Subject: Interface (Image)
//  → RealSubject: Actual object (RealImage)
//  → Proxy: Controls access to RealSubject (ProxyImage)
//  → Client: Works with Subject interface
//
//  KEY PARTICIPANTS:
//  → Subject: Common interface (display())
//  → RealSubject: Expensive/controlled object (RealImage)
//  → Proxy: Placeholder, controls access, delegates when appropriate
//
//  HOW VIRTUAL PROXY WORKS:
//  1. Proxy created (lightweight, no real object yet)
//  2. Client calls method on proxy
//  3. Proxy creates real object (lazy)
//  4. Proxy delegates to real object
//  5. Next call: real object already exists, just delegate
//
//  BENEFITS:
//  → Lazy initialization (save resources)
//  → Access control (security)
//  → Add functionality without modifying real object
//  → Hide remote object complexity
//
//  PROXY vs DECORATOR:
//  → DECORATOR: Adds functionality (new behavior)
//  → PROXY: Controls access (same behavior, with control)
//
//  PROXY vs ADAPTER:
//  → ADAPTER: Different interface (translation)
//  → PROXY: Same interface (control layer)
//
//  INTERVIEW ANSWER:
//  "Proxy controls access to an object without changing its interface.
//  For example, ProxyImage for lazy loading. Creating RealImage loads
//  from disk (expensive). ProxyImage is lightweight, only creates
//  RealImage when display() is first called. Next display() reuses it.
//  If I have 10 images but only view 2, only 2 RealImages are created.
//  Protection Proxy adds access control: check user role before allowing
//  display(). Remote Proxy hides network complexity. Spring AOP uses
//  proxies for transactions, caching, security."


// ============================================================
//  SUMMARY TABLE — All 7 Structural Patterns
// ============================================================
//
//  | Pattern    | Intent                          | Key Mechanism              |
//  |------------|----------------------------------|----------------------------|
//  | Adapter    | Make incompatible compatible    | Wrapper translates         |
//  | Bridge     | Decouple abstraction/impl       | Two hierarchies + composition |
//  | Composite  | Treat part/whole uniformly      | Tree structure, recursive  |
//  | Decorator  | Add responsibilities dynamically| Nested wrappers            |
//  | Facade     | Simplify complex subsystem      | Single entry point         |
//  | Flyweight  | Share state to save memory      | Intrinsic (shared) + Extrinsic (unique) |
//  | Proxy      | Control access                  | Placeholder, same interface|


// ============================================================
//  PATTERN RELATIONSHIPS — How They Connect
// ============================================================
//
//  ADAPTER + FACADE:
//  → Both wrap existing code
//  → Adapter: Changes interface (translation)
//  → Facade: Simplifies interface (convenience)
//
//  BRIDGE + ADAPTER:
//  → Bridge: Designed upfront (proactive)
//  → Adapter: Fixes existing incompatibility (reactive)
//
//  COMPOSITE + DECORATOR:
//  → Both involve recursive composition
//  → Composite: Part-whole hierarchy (tree)
//  → Decorator: Add responsibilities (chain)
//
//  DECORATOR + PROXY:
//  → Both wrap objects with same interface
//  → Decorator: Adds behavior
//  → Proxy: Controls access
//
//  FLYWEIGHT + SINGLETON:
//  → Flyweight: Pool of shared objects
//  → Singleton: Only ONE instance globally


// ============================================================
//  FINAL TIPS FOR INTERVIEWS
// ============================================================
//
//  1. LEAD WITH THE PROBLEM
//     Don't just define. Explain what breaks WITHOUT the pattern.
//
//  2. USE CONCRETE EXAMPLES
//     → Adapter: PayPal integration
//     → Bridge: Shape + RenderingAPI
//     → Composite: File system
//     → Decorator: Pizza toppings
//     → Facade: Computer startup
//     → Flyweight: Forest trees
//     → Proxy: Image lazy loading
//
//  3. CONNECT TO SOLID
//     → Adapter: Multiple adapters follow SRP, OCP
//     → Bridge: Prevents LSP violations from bad inheritance
//     → Decorator: OCP (extend via new decorators)
//     → Facade: Reduces coupling (DIP at system level)
//
//  4. KNOW REAL-WORLD USAGE
//     → Adapter: Java Collections (Arrays.asList())
//     → Composite: Swing UI components (JPanel contains JPanels)
//     → Decorator: Java I/O streams (BufferedReader wraps FileReader)
//     → Facade: SLF4J logging (facade over Log4j, Logback)
//     → Proxy: Spring AOP proxies
//
//  5. EXPLAIN TRADE-OFFS
//     → Decorator: More flexible than inheritance, but complex nesting
//     → Flyweight: Saves memory, but complexity in state separation
//     → Proxy: Adds control, but extra indirection
//
//  6. DRAW DIAGRAMS
//     If whiteboard available, sketch:
//     → Adapter: Target ← Adapter → Adaptee
//     → Bridge: Abstraction → Implementation
//     → Composite: Tree structure
//     → Decorator: Nested boxes
