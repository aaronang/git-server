package nl.tudelft.ewi.git.client;

import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.tudelft.ewi.git.models.GroupModel;
import nl.tudelft.ewi.git.models.IdentifiableModel;

/**
 * This class allows you to query and manipulate group members of a specific {@link GroupModel}.
 */
public class GroupMembersImpl extends Backend implements GroupMembers {

	private final GroupModel group;

	GroupMembersImpl(String host, GroupModel group) {
		super(host);
		this.group = group;
	}

	@Override
	public Collection<IdentifiableModel> listAll() {
		return perform(new Request<Collection<IdentifiableModel>>() {
			@Override
			public Collection<IdentifiableModel> perform(Client client) {
				return client.target(createUrl(group.getPath() + "/members"))
						.request(MediaType.APPLICATION_JSON)
						.get(new GenericType<Collection<IdentifiableModel>>() {
						});
			}
		});
	}

	@Override
	public Collection<IdentifiableModel> addMember(final IdentifiableModel identifiable) {
		return perform(new Request<Collection<IdentifiableModel>>() {
			@Override
			public Collection<IdentifiableModel> perform(Client client) {
				return client.target(createUrl(group.getPath() + "/members"))
						.request(MediaType.APPLICATION_JSON)
						.post(Entity.json(identifiable), new GenericType<Collection<IdentifiableModel>>() {
						});
			}
		});
	}

	@Override
	public void removeMember(final IdentifiableModel identifiable) {
		perform(new Request<Response>() {
			@Override
			public Response perform(Client client) {
				return client.target(createUrl(group.getPath() + "/members/" + encode(identifiable.getName())))
						.request()
						.delete(Response.class);
			}
		});
	}

}
