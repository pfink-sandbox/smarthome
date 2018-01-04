/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.automation.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * This class extends the {@link Exception} class functionality with functionality serving to accumulate the all
 * exceptions during the parsing process.
 *
 * @author Ana Dimova - Initial Contribution
 */
@SuppressWarnings("serial")
public class ParsingException extends Exception {

    /**
     * This field keeps all accumulated exceptions.
     */
    List<ParsingNestedException> exceptions;

    /**
     * This constructor creates the holder for one exception during the parsing process.
     *
     * @param e is an exception during the parsing process.
     */
    public ParsingException(ParsingNestedException e) {
        exceptions = new ArrayList<ParsingNestedException>();
        exceptions.add(e);
    }

    /**
     * This constructor creates a holder for several exceptions during the parsing process.
     *
     * @param exceptions is a list with exceptions during the parsing process.
     */
    public ParsingException(List<ParsingNestedException> exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    public String getMessage() {
        StringBuilder writer = new StringBuilder();
        for (ParsingNestedException e : exceptions) {
            writer.append(e.getMessage() + "\n");
        }
        return writer.toString();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        int size = 0;
        for (ParsingNestedException e : exceptions) {
            size = size + e.getStackTrace().length;
        }
        int index = 0;
        StackTraceElement[] st = new StackTraceElement[size];
        for (int n = 0; n < exceptions.size(); n++) {
            StackTraceElement[] ste = exceptions.get(n).getStackTrace();
            for (int i = 0; i < ste.length; i++) {
                st[index] = ste[i];
                index++;
            }
        }
        return st;
    }

}
