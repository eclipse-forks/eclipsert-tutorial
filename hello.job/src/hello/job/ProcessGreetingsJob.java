/**
 * Copyright (C) 2012 AGETO Service GmbH and others.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 * available at http://www.eclipse.org/org/documents/edl-v10.php
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package hello.job;

import hello.service.GreetingService;

import org.eclipse.gyrex.jobs.IJobContext;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A sample Eclipse {@link Job} which processes greetings.
 * <p>
 * Eclipse Jobs are executed in the background and allow to perform batch like
 * processing of data.
 * </p>
 */
public class ProcessGreetingsJob extends Job {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessGreetingsJob.class);

	private final GreetingService greetingService;
	private final IJobContext jobContext;

	/**
	 * Creates a new instance.
	 */
	public ProcessGreetingsJob(final GreetingService greetingService, final IJobContext jobContext) {
		super("Process Greetings");
		this.greetingService = greetingService;
		this.jobContext = jobContext;
		setPriority(LONG);
		setSystem(false);
	}

	public GreetingService getGreetingService() {
		return greetingService;
	}

	public IJobContext getJobContext() {
		return jobContext;
	}

	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		try {
			LOG.info("Processing greetings...");
			getGreetingService().processGreetings();
			for (int i = 0; (i < 30) && !monitor.isCanceled(); i++) {
				LOG.info("Job {} working...", getJobContext().getJobId());
				Thread.sleep(100 + RandomUtils.nextInt(400));
			}
			return Status.OK_STATUS;
		} catch (final IllegalStateException e) {
			LOG.error("Unable to process greetings. {}", e.getMessage(), e);
			return new Status(IStatus.CANCEL, "hello.cloud", "Unable to processing greetings. " + e.getMessage(), e);
		} catch (final Exception e) {
			LOG.error("Error processing greetings: {}", ExceptionUtils.getRootCauseMessage(e), e);
			return new Status(IStatus.ERROR, "hello.cloud", "Error processing greetings. " + e.getMessage(), e);
		}
	}

}