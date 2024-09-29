package com.kaba4cow.polyhaven.api.client.data;

import org.json.JSONObject;

/**
 * Represents an author from Polyhaven, containing information such as their ID,
 * name, portfolio link, email address, and donation information.
 */
public class PolyhavenAuthor {

	/**
	 * The unique ID of the author
	 **/
	private final String id;

	/**
	 * The author's full name, which may be different from the ID
	 **/
	private final String name;

	/**
	 * The author's preferred link to their portfolio
	 **/
	private final String link;

	/**
	 * Email address of the author
	 **/
	private final String email;

	/**
	 * Donation info of this author. May be a link to a donation page, or an email
	 * prefixed with paypal: to indicate a PayPal address
	 **/
	private final String donate;

	/**
	 * Constructs a {@code PolyhavenAuthor} with the given ID and JSON object.
	 *
	 * @param id   the unique identifier of the author
	 * @param json a {@code JSONObject} containing the author's details
	 */
	public PolyhavenAuthor(String id, JSONObject json) {
		this.id = id;
		this.name = json.getString("name");
		this.link = json.optString("link");
		this.email = json.optString("email");
		this.donate = json.optString("donate");
	}

	/**
	 * Returns the unique ID of the author.
	 *
	 * @return the unique ID of the author
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the full name of the author.
	 *
	 * @return the full name of the author
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the author's portfolio link.
	 *
	 * @return the portfolio link of the author
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Returns the author's email address.
	 *
	 * @return the email address of the author
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Returns the author's donation information.
	 *
	 * @return the donation information of the author
	 */
	public String getDonate() {
		return donate;
	}

	/**
	 * Returns a string representation of the author, including the ID, name, link,
	 * email, and donation information.
	 *
	 * @return a string representation of the {@code PolyhavenAuthor}
	 */
	@Override
	public String toString() {
		return String.format("%s [id=%s, name=%s, link=%s, email=%s, donate=%s]", getClass().getName(), id, name, link,
				email, donate);
	}

}
