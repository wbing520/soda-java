package com.socrata.model.importer;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Represents a permissions grant to a dataset.  Used whenever a dataset is made public
 * or explicitly shared to a user.
 */
public class Grant
{

    /**
     * A singleton that can be used to represent a public grant
     */
    public static final Grant PUBLIC_GRANT = new Grant(false, "viewer", null, ImmutableList.of("public"));

    /**
     * A predicate that can be used for determining if a grant is to make a dataset public.
     */
    public static final Predicate<Grant> IS_PUBLIC = new Predicate<Grant>()
    {
        @Override
        public boolean apply(@Nullable Grant input)
        {
            if (input == null || input.getFlags()==null) {
                return false;
            }

            return input.getFlags().contains("public");
        }
    };

    final boolean inherited;
    final String type;
    final String userId;
    final List<String> flags;



    @JsonCreator
    public Grant(@JsonProperty(value="inherited") boolean inherited,
                 @JsonProperty(value="type") String type,
                 @JsonProperty(value="userId") String userId,
                 @JsonProperty(value="flags") List<String> flags)
    {
        this.inherited = inherited;
        this.type = type;
        this.userId = userId;
        this.flags = flags;
    }

    /**
     * Whether this grant is inherited from a parent dataset or not.
     *
     * @return Whether this grant is inherited from a parent dataset or not.
     */
    public boolean isInherited()
    {
        return inherited;
    }

    /**
     * Returns the type of grant, such as viewer, etc.
     *
     * @return Returns the type of grant, such as viewer, etc.
     */
    public String getType()
    {
        return type;
    }


    /**
     * Returns the userid this grant is for.  This may be null if dealing witha  grant to make the
     * dataset public.
     *
     * @return
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * Gets the flags associated with this grant.
     *
     * @return
     */
    public List<String> getFlags()
    {
        return flags;
    }
}
