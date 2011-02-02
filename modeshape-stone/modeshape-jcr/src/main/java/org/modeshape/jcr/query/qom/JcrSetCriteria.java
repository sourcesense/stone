/*
 * ModeShape (http://www.modeshape.org)
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 * See the AUTHORS.txt file in the distribution for a full listing of 
 * individual contributors.
 *
 * ModeShape is free software. Unless otherwise indicated, all code in ModeShape
 * is licensed to you under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 * 
 * ModeShape is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.modeshape.jcr.query.qom;

import java.util.Collection;
import org.modeshape.graph.query.model.SetCriteria;

/**
 * Implementation of the same node constraint for the JCR Query Object Model and the Graph API.
 */
public class JcrSetCriteria extends SetCriteria implements JcrConstraint, org.modeshape.jcr.api.query.qom.SetCriteria {

    private static final long serialVersionUID = 1L;

    /**
     * @param left
     * @param setOperands
     */
    public JcrSetCriteria( JcrDynamicOperand left,
                           Collection<? extends JcrStaticOperand> setOperands ) {
        super(left, setOperands);
    }

    /**
     * @param left
     * @param setOperands
     */
    public JcrSetCriteria( JcrDynamicOperand left,
                           JcrStaticOperand... setOperands ) {
        super(left, setOperands);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.modeshape.jcr.api.query.qom.SetCriteria#getOperand()
     */
    @Override
    public javax.jcr.query.qom.DynamicOperand getOperand() {
        return (JcrDynamicOperand)super.leftOperand();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.modeshape.jcr.api.query.qom.SetCriteria#getValues()
     */
    @SuppressWarnings( "unchecked" )
    @Override
    public Collection<? extends javax.jcr.query.qom.StaticOperand> getValues() {
        return (Collection<JcrStaticOperand>)super.rightOperands();
    }

}
