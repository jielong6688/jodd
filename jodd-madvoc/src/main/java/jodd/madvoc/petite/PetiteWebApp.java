// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.madvoc.petite;

import jodd.madvoc.WebApp;
import jodd.petite.AutomagicPetiteConfigurator;
import jodd.petite.PetiteContainer;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * {@link WebApp WebApplication} that uses {@link jodd.petite.PetiteContainer Petite container}
 * for retrieving all instances.
 */
public class PetiteWebApp extends WebApp {

	public static final String PETITE_CONTAINER_NAME = "petiteContainer";

	/**
	 * Provides {@link PetiteContainer Petite container} instance that will be used as application context.
	 */
	private Supplier<PetiteContainer> petiteContainerSupplier = () -> {
		PetiteContainer pc = new PetiteContainer();
		new AutomagicPetiteConfigurator().configure(pc);
		return pc;
	};

	/**
	 * Supplies a Petite container to be used.
	 */
	public PetiteWebApp withPetiteContainer(final Supplier<PetiteContainer> petiteContainerSupplier) {
		Objects.requireNonNull(petiteContainerSupplier);
		this.petiteContainerSupplier = petiteContainerSupplier;
		return this;
	}

	@Override
	protected void registerMadvocComponents() {
		super.registerMadvocComponents();

		PetiteContainer petiteContainer = petiteContainerSupplier.get();

		madvocContainer.registerComponentInstance(PETITE_CONTAINER_NAME, petiteContainer);

		madvocContainer.registerComponent(PetiteMadvocController.class);
		madvocContainer.registerComponent(PetiteFilterManager.class);
		madvocContainer.registerComponent(PetiteInterceptorManager.class);
		madvocContainer.registerComponent(PetiteResultsManager.class);
	}

}