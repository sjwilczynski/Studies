package list9;

import javax.inject.Inject;

interface Fooable {
}

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

class A {
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
