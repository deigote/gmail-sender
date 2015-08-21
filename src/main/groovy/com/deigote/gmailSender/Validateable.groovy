package com.deigote.gmailSender

trait Validateable<A> {

	A validateOrFail() throws AssertionError {
		mandatoryFields.collectEntries { fieldName ->
			[(fieldName): this[fieldName]]
		}.each { fieldName, fieldValue ->
			assert fieldValue, "Invalid value '${fieldValue}' for ${fieldName}"
		}
		return this
	}

	abstract Set<String> getMandatoryFields()
}
