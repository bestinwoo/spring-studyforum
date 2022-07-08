package project.aha.common;

import static project.aha.common.ValidationGroups.*;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, NotEmptyGroup.class, LengthCheckGroup.class, PatternCheckGroup.class})
public interface ValidationSequence {
}
