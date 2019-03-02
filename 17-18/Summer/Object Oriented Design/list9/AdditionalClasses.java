package list9;

import javax.inject.Inject;

interface Fooable {
}

interface IA {}

class Foo implements Fooable {
}

class Bar implements Fooable {
}

class SubBar extends Bar {
}

class Woo {
    public Woo() {
    }

    private Woo(int i, int j, int k) {
    }
}

class Moo {
    Moo(int i) {
    }

    Moo(String s) {
    }
}

class A implements IA {
    A(Woo woo, Foo foo, B b) {
    }

    A() {
    }
}

class B {
    B(Bar bar, SubBar subBar, F f) {
    }
}

class C {
    C(C c) {
    }
}

class D {
    Foo foo;
    Woo woo;

    D(Foo foo, Woo woo) {
        this.foo = foo;
        this.woo = woo;
    }
}

class E {
    Foo foo;
    D d;

    E(Foo foo, D d) {
        this.foo = foo;
        this.d = d;
    }
}

class F {
    F(A a) {
    }
}

class G {
    Moo moo;

    G(Moo moo) {
        this.moo = moo;
    }
}

class H {
    public H(Fooable fooable) {
        this.fooable = fooable;
    }

    Fooable fooable;

}

class K {
    Foo foo;

    public K(int i, int j, int k) {
    }

    @Inject
    public K(Foo foo) {
        this.foo = foo;
    }
}

class L {
    @Inject
    public L() {
    }

    @Inject
    public L(Foo foo) {
    }
}


class M {

    Bar b1;
    Bar b2;

    public M(Bar b1, Bar b2) {
        this.b1 = b1;
        this.b2 = b2;
    }
}

class N {
    Bar b1;
    Bar b2;
    Bar b3;
    Bar b4;
    Bar b5;

    public N(Bar b){
        this.b1 = b;
    }

    @Inject
    public void setB1(Bar b2) {
        this.b2 = b2;
    }

    @Inject
    public void setB3(Bar b3) {
        this.b3 = b3;
    }

    @Inject
    public Bar setB4(Bar b4){
        this.b4 = b4;
        return b4;
    }

    public void setB5(Bar b5) {
        this.b5 = b5;
    }
}

class O {
    A a;

    @Inject
    public void setA(A a) {
        this.a = a;
    }
}