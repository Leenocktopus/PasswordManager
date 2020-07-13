@org.hibernate.annotations.GenericGenerator(
        name = "pm_generator",
        strategy = "enhanced-sequence",
        parameters = {
                @org.hibernate.annotations.Parameter(
                        name = "pm_sequence",
                        value = "JPWH_SEQUENCE"
                ),
                @org.hibernate.annotations.Parameter(
                        name = "initial_value",
                        value = "1"
                )
        }
)
package com.leandoer.logic.domain;