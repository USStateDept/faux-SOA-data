package faux;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Hash chained "random" data. Not thread safe.
 * 
 * @author root
 *
 */
public class FauxRandom extends Random
{
	private static final long serialVersionUID = 1L;
	private byte[] seed;
	private int seedpos = 0;

	public FauxRandom()
	{
		super(0);
	}

	public FauxRandom(byte seed[])
	{
		super(0);
		setSeed(seed);
	}

	synchronized public void setSeed(byte[] seed)
	{
		this.seed = seed;
	}

	public void setSeed(long seed){}

	/**
	 * 
	 * Generates a user-specified number of random bytes.
	 * 
	 * @param bytes
	 *            the array to be filled in with random bytes.
	 */

	synchronized public void nextBytes(byte[] bytes)
	{

		if (seed == null)
			throw new IllegalStateException("not seeded with a hash");

		if (bytes == null)
			return;

		for (int i = 0; i < bytes.length; ++i)
		{
			if (seedpos >= seed.length)
				reseed();
			bytes[i] = seed[seedpos++];
		}
	}

	static MessageDigest md;

	private void reseed()
	{
		if (md == null)
		{
			try
			{
				md = MessageDigest.getInstance("SHA-1");
			} catch (NoSuchAlgorithmException e)
			{
				throw new IllegalStateException(e);
			}
		}

		md.reset();
		md.update(seed);
		seed = md.digest();
		seedpos = 0;
	}

	/**
	 * 
	 * Generates an integer containing the user-specified number of
	 * pseudo-random bits (right justified, with leading zeros). This method
	 * overrides a <code>java.util.Random</code> method, and serves to provide a
	 * source of random bits to all of the methods inherited from that class
	 * (for example, <code>nextInt</code>, <code>nextLong</code>, and
	 * <code>nextFloat</code>).
	 * 
	 * @param numBits
	 *            number of pseudo-random bits to be generated, where 0 <=
	 *            <code>numBits</code> <= 32.
	 * 
	 * @return an <code>int</code> containing the user-specified number of
	 *         pseudo-random bits (right justified, with leading zeros).
	 */

	final protected int next(int numBits)
	{
		int numBytes = (numBits + 7) / 8;
		byte b[] = new byte[numBytes];
		int next = 0;
		nextBytes(b);
		for (int i = 0; i < numBytes; i++)
			next = (next << 8) + (b[i] & 0xFF);
		return next >>> (numBytes * 8 - numBits);
	}
}
