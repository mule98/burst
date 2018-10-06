/*
package org.mule.burster;

import java.util.function.Function;

@FunctionalInterface
public class Ticket<T, Z> {

    private final Function<T, Z> material;

    public Ticket(Function<T, Z> material){

        this.material = material;
    }

    public Ticket(Ticket<T, Z> tzTicket, Ticket<T, Z> ticket) {

    }

    Z burn(T input){
        return material.apply(input);
    }

    <Z,W> Ticket<Z, W> append(Ticket<Z, W> ticket){
        return new Ticket<Z, W>(this, ticket);
    }
}
*/
