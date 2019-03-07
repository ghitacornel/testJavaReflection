INTROSPECTION / DISCOVERY



Java Reflection allows to discover a class :

- annotations
- fields ( along with field annotations, qualifiers, access modifiers, etc )
- constructors ( along with constructors annotations, qualifiers, access modifiers, etc )
- methods ( along with methods annotations, qualifiers, access modifiers, etc )
- implemented interfaces
- parents class hierarchy (inherited classes / fields / methods / constructors / annotations)

See

- java.lang.ClassLoader
- java.lang.Class
- java.lang.Class.forName(java.lang.String)
- java.lang.reflect.Field
- java.lang.reflect.Constructor
- java.lang.reflect.Method
- java.lang.reflect.Modifier
- java.lang.annotation.Annotation

Exercise :

design some classes, interfaces, class hierarchies and annotations
use Java Reflection in order to obtain information about classes from actual objects



METHOD INVOCATION



Java Reflection allows invocation of class methods.

It is possible to invoke different types of methods like :

- static / non static
- final / non final
- public / private / protected / default
- with parameters / with no parameters / with variable numbers of parameters

See

- java.lang.reflect.Method#invoke
- java.lang.reflect.Method#setAccessible

Exercise :

Using Java Reflection
Invoke a public / private / protected method with parameters on an object
Invoke a static public / private / protected method with parameters method



CONSTRUCTOR INVOCATION


Java Reflection allows the creation of class instances by invoking class constructors.

It is possible to invoke different types of constructors :

- public / private / protected / default
- with parameters / with no parameters / with variable numbers of parameters

See :

- java.lang.reflect.Constructor#newInstance
- java.lang.reflect.Constructor#setAccessible

Exercise :

Using Java Reflection create a class instance
Use a (public/private/protected) constructor (with/without) parameters




CHANGE FIELD VALUE




Java Reflection allows to get and set the value of a class instance field.

It is possible to change values of different types of class fields :

- static / non static
- public / private / protected / default
- final / non final

See :

- java.lang.reflect.Field#get
- java.lang.reflect.Field#set
- java.lang.reflect.Field#setAccessible

Note :

- changing final static fields values pose a problem in terms of data inconsistency
If a field is a constant variable (§4.12.4),
then deleting the keyword final or changing its value
will not break compatibility with pre-existing binaries by causing them not to run,
but they will not see any new value for the usage of the field unless they are recompiled.
This is true even if the usage itself is not a compile-time constant expression (§15.28)

Exercise :

Using Java Reflection alter a class instance field values
Alter static / non static field values
Alter final / non final field values
Alter private / default / protected / public field values
