export interface ProgressItem {
  id: string
  title: string
}

export interface ProgressSection {
  id: string
  title: string
  items: ProgressItem[]
}

export interface ProgressDomain {
  id: string
  title: string
  sections: ProgressSection[]
}

export const progressTree: ProgressDomain[] = [
  {
    id: '1',
    title: 'Communication and interaction',
    sections: [
      {
        id: '1.1',
        title: 'Engaging in interaction',
        items: [
          { id: '1.1.1', title: 'Responds positively to familiar adult' },
          { id: '1.1.2', title: 'Seeks attention from familiar adult' },
          { id: '1.1.3', title: 'Shares attention focus with adult' },
          { id: '1.1.4', title: 'Engages in interactive exchange with adult' },
        ],
      },
      {
        id: '1.2',
        title: 'Making requests',
        items: [
          { id: '1.2.1', title: 'Makes request for an item' },
          { id: '1.2.2', title: 'Requests an item using gesture, picture or word' },
          { id: '1.2.3', title: 'Makes request for help' },
          { id: '1.2.4', title: 'Requests information or assistance' },
        ],
      },
      {
        id: '1.3',
        title: 'Communicating information / commenting on events',
        items: [
          { id: '1.3.1', title: 'Answers a question' },
          { id: '1.3.2', title: 'Communicates information about past and future events' },
          { id: '1.3.3', title: 'Expresses opinions, thoughts or feelings' },
          { id: '1.3.4', title: 'Gives instructions or explanations' },
          { id: '1.3.5', title: 'Gives accounts and descriptions' },
          { id: '1.3.6', title: 'Comments draws attention to an event' },
        ],
      },
      {
        id: '1.4',
        title: 'Listening and understanding',
        items: [
          { id: '1.4.1', title: 'Responds to sounds' },
          { id: '1.4.2', title: 'Understands single spoken word' },
          { id: '1.4.3', title: 'Understands simple statements' },
          { id: '1.4.4', title: 'Understands instructions' },
          { id: '1.4.5', title: 'Understands questions' },
          { id: '1.4.6', title: 'Understands abstract meaning' },
          { id: '1.4.7', title: 'Understands humour and figurative speech' },
          { id: '1.4.8', title: 'Understands idioms, sarcasm or jokes' },
        ],
      },
      {
        id: '1.5',
        title: 'Greetings',
        items: [
          { id: '1.5.1', title: 'Responds to greetings' },
          { id: '1.5.2', title: 'Greets others' },
        ],
      },
      {
        id: '1.6',
        title: 'Conversations',
        items: [
          { id: '1.6.1', title: 'Gains another’s attention' },
          { id: '1.6.2', title: 'Takes lead in conversation' },
          { id: '1.6.3', title: 'Responds to conversation partner' },
          { id: '1.6.4', title: 'Maintains flow of conversation' },
        ],
      },
      {
        id: '1.7',
        title: 'Non-verbal communication',
        items: [
          { id: '1.7.1', title: 'Adapts communication behaviour to suit situation' },
          { id: '1.7.2', title: 'Shows evidence of active listening' },
          { id: '1.7.3', title: 'Understands non-verbal communication' },
        ],
      },
    ],
  },
  {
    id: '2',
    title: 'Social understanding and relationships',
    sections: [
      {
        id: '2.1',
        title: 'Being with others',
        items: [
          { id: '2.1.1', title: 'Accepts presence of others in familiar environment' },
          { id: '2.1.2', title: 'Engages with shared activity' },
          { id: '2.1.3', title: 'Copes with proximity of others in public space' },
        ],
      },
      {
        id: '2.2',
        title: 'Interactive play',
        items: [
          { id: '2.2.1', title: 'Accepts presence of adult in play environment' },
          { id: '2.2.2', title: 'Engages in interactive play with adult' },
          { id: '2.2.3', title: 'Engages in object play with adult' },
          { id: '2.2.4', title: 'Engages in play with peers' },
        ],
      },
      {
        id: '2.3',
        title: 'Positive relationships (supporting adults)',
        items: [
          { id: '2.3.1', title: 'Engages positively with supporting adult' },
          { id: '2.3.2', title: 'Requests help from adult' },
          { id: '2.3.3', title: 'Accepts advice, assistance or support' },
          { id: '2.3.4', title: 'Seeks advice or support from adult' },
        ],
      },
      {
        id: '2.4',
        title: 'Positive relationships and friendships (peers)',
        items: [
          { id: '2.4.1', title: 'Initiates interaction with peers' },
          { id: '2.4.2', title: 'Engages positively in interaction with peers' },
          { id: '2.4.3', title: 'Takes account of others’ interests, needs or feelings' },
          { id: '2.4.4', title: 'Takes action to sustain positive relationship' },
          { id: '2.4.5', title: 'Recognises negative or bullying behaviour towards self or others' },
        ],
      },
      {
        id: '2.5',
        title: 'Group activities',
        items: [
          { id: '2.5.1', title: 'Attends to focus of group' },
          { id: '2.5.2', title: 'Participates in group activity' },
          { id: '2.5.3', title: 'Is aware of self as part of group' },
          { id: '2.5.4', title: 'Understands and conforms to expectations of working in a group' },
          { id: '2.5.5', title: 'Participates in group discussion' },
        ],
      },
    ],
  },
  {
    id: '3',
    title: 'Sensory processing',
    sections: [
      {
        id: '3.1',
        title: 'Understanding and expressing own sensory needs',
        items: [
          { id: '3.1.1', title: 'Expresses sensory likes or dislikes' },
          { id: '3.1.2', title: 'Understands own sensory needs' },
        ],
      },
      {
        id: '3.2',
        title: 'Responding to sensory interventions',
        items: [
          { id: '3.2.1', title: 'Responds to sensory adaptations in the environment' },
          { id: '3.2.2', title: 'Responds to prescribed sensory support' },
          { id: '3.2.3', title: 'Responds to sensory strategies' },
          { id: '3.2.4', title: 'Responds to regular sensory programmes' },
        ],
      },
      {
        id: '3.3',
        title: 'Increasing tolerance of sensory input',
        items: [
          { id: '3.3.1', title: 'Shows increased tolerance of sensory input' },
        ],
      },
      {
        id: '3.4',
        title: 'Managing own sensory needs',
        items: [
          { id: '3.4.1', title: 'Accepts support to manage own behaviour in relation to sensory needs' },
          { id: '3.4.2', title: 'Requests others’ help to manage sensory needs' },
          { id: '3.4.3', title: 'Takes action to manage own sensory needs' },
          { id: '3.4.4', title: 'Reflects on sensory needs and behaviour' },
        ],
      },
    ],
  },
  {
    id: '4',
    title: 'Interests, routines and processing',
    sections: [
      {
        id: '4.1',
        title: 'Coping with change',
        items: [
          { id: '4.1.1', title: 'Accepts change within familiar situations' },
          { id: '4.1.2', title: 'Reacts calmly to change' },
        ],
      },
      {
        id: '4.2',
        title: 'Transitions',
        items: [
          { id: '4.2.1', title: 'Makes successful transition in everyday situations' },
          { id: '4.2.2', title: 'Engages with preparation for transition to new setting' },
        ],
      },
      {
        id: '4.3',
        title: 'Special interests',
        items: [
          { id: '4.3.1', title: 'Uses special interests to engage positively in activities' },
          { id: '4.3.2', title: 'Exchanges with range of activities unrelated to special interests' },
        ],
      },
      {
        id: '4.4',
        title: 'Problem solving and thinking skills',
        items: [
          { id: '4.4.1', title: 'Makes a choice' },
          { id: '4.4.2', title: 'Uses information available to make an appropriate choice' },
          { id: '4.4.3', title: 'Sorts items into categories' },
          { id: '4.4.4', title: 'Uses information to plan and predict' },
          { id: '4.4.5', title: 'Makes decisions based on information available' },
          { id: '4.4.6', title: 'Recognises and talks about problems' },
          { id: '4.4.7', title: 'Reflects on problems encountered and strategies used' },
        ],
      },
    ],
  },
  {
    id: '5',
    title: 'Emotional understanding and self-awareness',
    sections: [
      {
        id: '5.1',
        title: 'Understanding emotions',
        items: [
          { id: '5.1.1', title: 'Recognises own emotions' },
          { id: '5.1.2', title: 'Recognises emotions of others' },
        ],
      },
      {
        id: '5.2',
        title: 'Expressing emotions',
        items: [
          { id: '5.2.1', title: 'Expresses emotions appropriately' },
          { id: '5.2.2', title: 'Expresses feelings using words, symbols or behaviour' },
        ],
      },
      {
        id: '5.3',
        title: 'Emotional regulation',
        items: [
          { id: '5.3.1', title: 'Uses strategies to manage emotions' },
          { id: '5.3.2', title: 'Accepts support to regulate emotions' },
        ],
      },
      {
        id: '5.4',
        title: 'Self-awareness',
        items: [
          { id: '5.4.1', title: 'Shows awareness of own strengths' },
          { id: '5.4.2', title: 'Shows awareness of own needs' },
        ],
      },
    ],
  },
  {
    id: '6',
    title: 'Learning and engagement',
    sections: [
      {
        id: '6.1',
        title: 'Engagement',
        items: [
          { id: '6.1.1', title: 'Engages with adult-led activity' },
          { id: '6.1.2', title: 'Engages with independent activity' },
        ],
      },
      {
        id: '6.2',
        title: 'Attention',
        items: [
          { id: '6.2.1', title: 'Maintains attention to task' },
          { id: '6.2.2', title: 'Sustains attention for increasing periods' },
        ],
      },
      {
        id: '6.3',
        title: 'Motivation',
        items: [
          { id: '6.3.1', title: 'Shows motivation to learn' },
          { id: '6.3.2', title: 'Persists with challenging tasks' },
        ],
      },
      {
        id: '6.4',
        title: 'Learning strategies',
        items: [
          { id: '6.4.1', title: 'Uses strategies to support learning' },
          { id: '6.4.2', title: 'Reflects on own learning' },
        ],
      },
    ],
  },
  {
    id: '7',
    title: 'Healthy living',
    sections: [
      {
        id: '7.1',
        title: 'Physical health',
        items: [
          { id: '7.1.1', title: 'Maintains personal hygiene' },
          { id: '7.1.2', title: 'Manages toileting needs' },
        ],
      },
      {
        id: '7.2',
        title: 'Nutrition',
        items: [
          { id: '7.2.1', title: 'Makes healthy food choices' },
          { id: '7.2.2', title: 'Accepts a range of foods' },
        ],
      },
      {
        id: '7.3',
        title: 'Physical activity',
        items: [
          { id: '7.3.1', title: 'Participates in physical activity' },
          { id: '7.3.2', title: 'Understands importance of physical activity' },
        ],
      },
      {
        id: '7.4',
        title: 'Safety',
        items: [
          { id: '7.4.1', title: 'Recognises unsafe situations' },
          { id: '7.4.2', title: 'Takes action to keep self safe' },
        ],
      },
    ],
  },
  {
    id: '8',
    title: 'Independence and community participation',
    sections: [
      {
        id: '8.1',
        title: 'Independence skills',
        items: [
          { id: '8.1.1', title: 'Completes self-care tasks independently' },
          { id: '8.1.2', title: 'Manages personal belongings' },
        ],
      },
      {
        id: '8.2',
        title: 'Decision making',
        items: [
          { id: '8.2.1', title: 'Makes choices independently' },
          { id: '8.2.2', title: 'Understands consequences of choices' },
        ],
      },
      {
        id: '8.3',
        title: 'Community participation',
        items: [
          { id: '8.3.1', title: 'Participates in community activities' },
          { id: '8.3.2', title: 'Uses community facilities appropriately' },
        ],
      },
      {
        id: '8.4',
        title: 'Preparation for adulthood',
        items: [
          { id: '8.4.1', title: 'Develops skills for future independence' },
          { id: '8.4.2', title: 'Understands roles and responsibilities' },
        ],
      },
    ],
  },
]
