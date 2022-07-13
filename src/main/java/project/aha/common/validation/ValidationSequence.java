package project.aha.common.validation;

import static project.aha.common.validation.ValidationGroups.*;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, NotEmptyGroup.class, LengthCheckGroup.class, PatternCheckGroup.class})
public interface ValidationSequence {
}
